package com.jeongbj.glim.external.ai.gradio.client

import com.jeongbj.glim.external.ai.gradio.config.GradioProperties
import com.jeongbj.glim.external.ai.gradio.dto.GradioEventResponse
import com.jeongbj.glim.external.ai.gradio.dto.GradioRequest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.MediaType
import org.springframework.http.codec.ServerSentEvent
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBodilessEntity
import org.springframework.web.reactive.function.client.awaitBody
import org.springframework.web.reactive.function.client.bodyToMono

@Component
class GradioClient(
    @Qualifier("gradioWebClient")
    private val gradioWebClient: WebClient,
    private val properties: GradioProperties
) {
    suspend fun wakeUpSpaces() {
        for (url in properties.urls) {
            println(url)
            runCatching {
                gradioWebClient.get()
                    .uri("$url/gradio_api/info")
                    .headers {
                        it.setBearerAuth(properties.keys[0])
                    }
                    .retrieve()
                    .awaitBodilessEntity()
            }.onFailure { println(it) }
        }
    }

    suspend fun generateImage(prompt: String): ByteArray {
        for (url in properties.urls) {
            for (key in properties.keys) {
                try {
                    val event = createEvent(prompt, url, key)
                    val imageUrl = getResult(event.eventId, url, key)
                    val image = downloadImage(imageUrl, key)
                    return image
                } catch (e: Exception) {
                    continue
                }
            }
        }
        throw IllegalStateException("All HF Servers Failed")
    }

    suspend fun createEvent(prompt: String, url: String, key: String)
    : GradioEventResponse {
        return gradioWebClient.post()
            .uri("$url/gradio_api/call/infer")
            .headers {
                it.setBearerAuth(key)
            }
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(
                GradioRequest(data = listOf(
                    prompt,
                    0,
                    true,
                    properties.width,
                    properties.height,
                    4
                ))
            )
            .retrieve()
            .awaitBody<GradioEventResponse>()
    }

    suspend fun getResult(eventId: String, url: String, key: String): String {
        println(eventId)
        val event = gradioWebClient.get()
            .uri("$url/gradio_api/call/infer/$eventId")
            .headers {
                it.setBearerAuth(key)
            }
            .accept(MediaType.TEXT_EVENT_STREAM)
            .retrieve()
            .bodyToFlux(ServerSentEvent::class.java)
            .doOnNext {
                println("event=${it.event()} data=${it.data()}")
            }
            .asFlow()
            .first { it.event() == "complete" || it.event() == "error" }

        if (event.event() == "error") {
            throw IllegalStateException("Gradio returned error")
        }

        return parseResult(event.data()!!)
    }

    private fun parseResult(data: Any): String {
        val list = data as List<*>
        val file = list[0] as Map<*, *>
        return file["url"] as String
    }

    private suspend fun downloadImage(url: String, key: String): ByteArray {
        val response = WebClient.create()
            .get()
            .uri(url)
            .headers {
                it.setBearerAuth(key)
            }
            .exchangeToMono { res ->
                println(res.statusCode())
                println(res.headers().contentType())
                res.bodyToMono<ByteArray>()
            }
            .awaitSingle()

        return response
    }
}