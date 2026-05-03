package com.jeongbj.glim

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GlimServerApplication

fun main(args: Array<String>) {
    runApplication<GlimServerApplication>(*args)
}
