package com.jeongbj.glim.infra.bucket

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "oci")
data class OciProperties(
    val profile: String = "DEFAULT",
    val configPath: String,
    val namespace: String,
    val bucket: String,
    val region: String
)