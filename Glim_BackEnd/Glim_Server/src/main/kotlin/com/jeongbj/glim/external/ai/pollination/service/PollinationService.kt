package com.jeongbj.glim.external.ai.pollination.service

import com.jeongbj.glim.external.ai.pollination.client.PollinationClient
import org.springframework.stereotype.Service

@Service
class PollinationService(
    private val client: PollinationClient,
) {
    fun generateImage(prompt: String): ByteArray {
        return client.generateImage(prompt)
    }
}