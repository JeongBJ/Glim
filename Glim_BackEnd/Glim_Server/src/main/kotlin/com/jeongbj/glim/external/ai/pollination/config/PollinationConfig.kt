package com.jeongbj.glim.external.ai.pollination.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.ExchangeStrategies
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient
import java.time.Duration

@Configuration
class PollinationConfig(
    private val pollinationProperties: PollinationProperties,
) {

    @Bean
    fun pollinationWebClient(): WebClient {
        val strategies = ExchangeStrategies.builder()
            .codecs { configurer ->
                configurer.defaultCodecs()
                    .maxInMemorySize(10 * pollinationProperties.width * pollinationProperties.height)
            }
            .build()
        return WebClient.builder()
            .baseUrl(pollinationProperties.baseUrl)
            .exchangeStrategies(strategies)
            .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer ${pollinationProperties.key}")
            .defaultHeader(HttpHeaders.ACCEPT, "image/jpeg, image/png")
            .clientConnector(
                ReactorClientHttpConnector(
                    HttpClient.create()
                        .responseTimeout(Duration.ofSeconds(30))
                )
            )
            .build()
    }

}