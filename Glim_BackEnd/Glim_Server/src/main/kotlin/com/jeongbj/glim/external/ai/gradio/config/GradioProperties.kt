package com.jeongbj.glim.external.ai.gradio.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "gradio")
data class GradioProperties(
    val baseUrL: String,
    val key: String,
    val height: Int,
    val width: Int
)