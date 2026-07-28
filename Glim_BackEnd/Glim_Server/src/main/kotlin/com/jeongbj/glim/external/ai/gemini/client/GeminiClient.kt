package com.jeongbj.glim.external.ai.gemini.client

import com.jeongbj.glim.external.ai.gemini.config.GeminiProperties
import com.jeongbj.glim.external.ai.gemini.dto.request.Content
import com.jeongbj.glim.external.ai.gemini.dto.request.GeminiRequest
import com.jeongbj.glim.external.ai.gemini.dto.request.Part
import com.jeongbj.glim.external.ai.gemini.dto.response.GeminiResponse
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono

@Component
class GeminiClient(
    @Qualifier("geminiWebClient")
    private val geminiWebClient: WebClient,
    private val properties: GeminiProperties
) {

    fun generateText(prompt: String): String {
        val request = GeminiRequest(
            contents = listOf(Content(
                parts = listOf(Part(prompt)))
            )
        )

        val response = geminiWebClient.post()
            .uri {
                it.path("/v1beta/models/${properties.model}:generateContent")
                    .build()
            }
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(request)
            .retrieve()
            .bodyToMono<GeminiResponse>()
            .block()
            ?: throw IllegalArgumentException("Gemini Response is Null")

        return response.candidates.firstOrNull()
            ?.content?.parts?.firstOrNull()
            ?.text ?: throw IllegalArgumentException("Gemini Response is Empty")
    }

}