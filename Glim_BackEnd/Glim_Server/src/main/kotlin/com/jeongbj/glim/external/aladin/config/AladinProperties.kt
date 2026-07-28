package com.jeongbj.glim.external.aladin.config

import org.springframework.boot.context.properties.ConfigurationProperties


@ConfigurationProperties(prefix = "aladin")
data class AladinProperties(
    val ttbKey: String,
    val output: String,
    val version: String
)
