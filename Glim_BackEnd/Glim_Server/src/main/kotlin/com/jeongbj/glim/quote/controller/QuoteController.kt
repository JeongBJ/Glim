package com.jeongbj.glim.quote.controller

import com.jeongbj.glim.common.response.BaseResponse
import com.jeongbj.glim.quote.dto.GenerateImageRequest
import com.jeongbj.glim.quote.dto.QuoteRequest
import com.jeongbj.glim.quote.dto.QuoteResponse
import com.jeongbj.glim.quote.service.QuoteService
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/quote")
class QuoteController (
    private val quoteService: QuoteService
) {

    @PostMapping
    fun saveQuote(
        @AuthenticationPrincipal userSeq: Long,
        @RequestPart("request") quoteRequest: QuoteRequest,
        @RequestPart("image") image: MultipartFile
    ) : ResponseEntity<BaseResponse<QuoteResponse>> {
        val response = quoteService.saveQuote(userSeq, quoteRequest, image)
        return ResponseEntity.ok(BaseResponse.success(response, "업로드 성공"))
    }

    @PostMapping("/generate")
    fun generateImage(@RequestBody request: GenerateImageRequest): ResponseEntity<ByteArray> {
        return ResponseEntity.ok()
            .contentType(MediaType.IMAGE_JPEG)
            .body(quoteService.generateImage(request.content))
    }
}