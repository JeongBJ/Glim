package com.jeongbj.glim.external.ai.gemini.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "gemini")
data class GeminiProperties(
    val key: String,
    val model: String,
    val baseUrl: String
)
