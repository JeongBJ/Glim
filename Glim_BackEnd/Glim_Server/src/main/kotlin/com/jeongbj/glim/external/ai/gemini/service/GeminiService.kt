package com.jeongbj.glim.external.ai.gemini.service

import com.jeongbj.glim.external.ai.gemini.client.GeminiClient
import org.springframework.stereotype.Service

@Service
class GeminiService(
    private val geminiClient: GeminiClient
) {
    fun generateText(content: String): String {
        val prompt = """
            아래 문장에 어울리는 FLUX 모델용 프롬프트를 생성해줘. 응답은 영문 프롬프트만 해줘
            문장: $content
        """.trimIndent()
        return geminiClient.generateText(prompt)
    }
}