package com.jeongbj.glim.external.ai.gemini.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient
import java.time.Duration

@Configuration
class GeminiConfig(
    private val geminiProperties: GeminiProperties
) {
    @Bean
    fun geminiWebClient(): WebClient =
        WebClient.builder()
            .baseUrl(geminiProperties.baseUrl)
            .clientConnector(ReactorClientHttpConnector(
                HttpClient.create()
                    .responseTimeout(Duration.ofSeconds(30))
            ))
            .defaultHeader("x-goog-api-key", geminiProperties.key)
            .build()
}