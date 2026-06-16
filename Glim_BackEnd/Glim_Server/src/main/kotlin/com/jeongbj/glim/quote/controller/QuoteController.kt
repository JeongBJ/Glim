package com.jeongbj.glim.quote.controller

import com.jeongbj.glim.common.dto.CursorPage
import com.jeongbj.glim.common.response.BaseResponse
import com.jeongbj.glim.quote.dto.GenerateImageRequest
import com.jeongbj.glim.quote.dto.QuoteCursor
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
        val body = runCatching {
            quoteService.generateImage(request.content)
        }.onFailure {
            println(it)
        }.getOrNull()

        return ResponseEntity.ok()
            .contentType(MediaType.IMAGE_JPEG)
            .body(body)
    }

    @GetMapping
    fun getQuotes(
        @AuthenticationPrincipal userSeq: Long,
        @RequestParam(required = false) seed: Long?,
        @RequestParam(required = false) size: Long?,
        @RequestParam(required = false) score: Long?,
        @RequestParam(required = false) quoteSeq: Long?,
        ): ResponseEntity<BaseResponse<CursorPage<QuoteResponse, QuoteCursor>>> {
        val cursor =
            if (score != null && quoteSeq != null)
                QuoteCursor(score, quoteSeq)
            else null


        val data = quoteService.getQuotes(
            seed = seed,
            cursor = cursor,
            size = size ?: 20,
            userSeq = userSeq
        )
        return ResponseEntity.ok(BaseResponse.success(data, "글림 조회 성공"))
    }

    @GetMapping("/view/{quoteSeq}")
    fun increaseView(@PathVariable quoteSeq: Long): ResponseEntity<BaseResponse<Unit>> {
        quoteService.increaseView(quoteSeq)
        return ResponseEntity.ok(BaseResponse.success(Unit, "조회수 증가됨"))
    }

    @GetMapping("/{quoteSeq}")
    fun getQuote(@PathVariable quoteSeq: Long, @AuthenticationPrincipal userSeq: Long)
    : ResponseEntity<BaseResponse<QuoteResponse>> {
        val quote = quoteService.getQuote(quoteSeq, userSeq)
        return ResponseEntity.ok(BaseResponse.success(quote, "글림 조회 성공"))
    }
}

