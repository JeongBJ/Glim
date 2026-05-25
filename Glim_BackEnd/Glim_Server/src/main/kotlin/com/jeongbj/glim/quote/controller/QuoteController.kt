package com.jeongbj.glim.quote.controller

import com.jeongbj.glim.common.response.BaseResponse
import com.jeongbj.glim.quote.dto.QuoteRequest
import com.jeongbj.glim.quote.dto.QuoteResponse
import com.jeongbj.glim.quote.service.QuoteService
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/quote")
class QuoteController (
    private val quoteService: QuoteService
) {

    @PostMapping
    fun saveQuote(
        @AuthenticationPrincipal userSeq: Long,
        @RequestBody quoteRequest: QuoteRequest
    ) : ResponseEntity<BaseResponse<QuoteResponse>> {
        val response = quoteService.saveQuote(userSeq, quoteRequest)
        return ResponseEntity.ok(BaseResponse.success(response, "업로드 성공"))
    }

    @GetMapping
    fun test(): ResponseEntity<ByteArray> {
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(quoteService.test())
    }
}