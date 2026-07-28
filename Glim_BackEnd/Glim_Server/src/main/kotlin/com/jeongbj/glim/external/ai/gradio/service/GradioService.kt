package com.jeongbj.glim.external.ai.gradio.service

import com.jeongbj.glim.external.ai.gradio.client.GradioClient
import kotlinx.coroutines.runBlocking
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class GradioService(
    private val client: GradioClient
) {
    suspend fun generateImage(prompt: String): ByteArray {
        return client.generateImage(prompt)
    }

    @Scheduled(cron = "0 0 7 * * *")
    fun wakeUpSpaces() {
        runBlocking {
            client.wakeUpSpaces()
        }
    }
}