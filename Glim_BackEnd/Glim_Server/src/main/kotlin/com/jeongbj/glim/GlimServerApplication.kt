package com.jeongbj.glim

import com.jeongbj.glim.infra.bucket.OciProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(OciProperties::class)
class GlimServerApplication

fun main(args: Array<String>) {
    runApplication<GlimServerApplication>(*args)
}
