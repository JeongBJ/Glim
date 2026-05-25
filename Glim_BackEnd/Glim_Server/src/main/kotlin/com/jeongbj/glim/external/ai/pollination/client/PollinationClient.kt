package com.jeongbj.glim.external.ai.pollination.client

import com.jeongbj.glim.external.ai.pollination.config.PollinationProperties
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono

@Component
class PollinationClient(
    @Qualifier("pollinationWebClient")
    private val pollinationWebClient: WebClient,
    private val properties: PollinationProperties
) {
    fun generateImage(prompt: String): ByteArray {
        return pollinationWebClient.get()
            .uri { builder ->
                builder
                    .path("/prompt/{prompt}")
                    .queryParam("model", properties.model)
                    .queryParam("width", properties.width)
                    .queryParam("height", properties.height)
                    .build(prompt)
            }
            .retrieve()
            .bodyToMono<ByteArray>()
            .block()
            ?: throw IllegalArgumentException("Failed to Generate Image")
    }

}