package com.jeongbj.glim.common.config

import com.jeongbj.glim.external.ai.pollination.config.PollinationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient
import java.time.Duration

@Configuration
class WebClientConfig(
    private val pollinationProperties: PollinationProperties
) {

    @Bean
    fun pollinationWebClient(): WebClient =
        WebClient.builder()
            .baseUrl(pollinationProperties.baseUrl)
            .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer ${pollinationProperties.key}")
            .defaultHeader(HttpHeaders.ACCEPT, "image/jpeg, image/png")
            .clientConnector(ReactorClientHttpConnector(
                HttpClient.create()
                    .responseTimeout(Duration.ofSeconds(30))))
            .build()

}