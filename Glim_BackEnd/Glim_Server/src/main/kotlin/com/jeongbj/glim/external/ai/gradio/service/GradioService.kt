package com.jeongbj.glim.external.ai.gradio.service

import com.jeongbj.glim.external.ai.gradio.client.GradioClient
import org.springframework.stereotype.Service

@Service
class GradioService(
    private val client: GradioClient
) {
    suspend fun generateImage(prompt: String): ByteArray {
        return client.generateImage(prompt)
    }
}