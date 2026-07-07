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
import org.springframework.web.reactive.function.client.awaitBody
import org.springframework.web.reactive.function.client.bodyToMono

@Component
class GradioClient(
    @Qualifier("gradioWebClient")
    private val gradioWebClient: WebClient,
    private val properties: GradioProperties
) {

    suspend fun generateImage(prompt: String): ByteArray {
        val event = createEvent(prompt)
        val imageUrl = getResult(event.eventId)
        val image = downloadImage(imageUrl)
        return image
    }

    suspend fun createEvent(prompt: String)
    : GradioEventResponse {
        return gradioWebClient.post()
            .uri("/gradio_api/call/infer")
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

    suspend fun getResult(eventId: String): String {
        println(eventId)
        val event = gradioWebClient.get()
            .uri("/gradio_api/call/infer/$eventId")
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

    private suspend fun downloadImage(url: String): ByteArray {
        val response = WebClient.create()
            .get()
            .uri(url)
            .headers {
                it.setBearerAuth(properties.key)
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