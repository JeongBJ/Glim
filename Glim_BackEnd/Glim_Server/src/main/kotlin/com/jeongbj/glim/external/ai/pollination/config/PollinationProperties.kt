package com.jeongbj.glim.external.ai.pollination.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "pollination")
data class PollinationProperties(
    val baseUrl: String,
    val key: String,
    val height: Int,
    val width: Int,
    val model: String
)