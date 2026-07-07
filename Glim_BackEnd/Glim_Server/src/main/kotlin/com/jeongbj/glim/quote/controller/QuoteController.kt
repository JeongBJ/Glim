package com.jeongbj.glim.quote.controller

import com.jeongbj.glim.common.dto.CursorPage
import com.jeongbj.glim.common.response.BaseResponse
import com.jeongbj.glim.quote.dto.*
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

    @PostMapping("/insert")
    fun saveQuote(
        @AuthenticationPrincipal userSeq: Long,
        @RequestPart("request") quoteRequest: QuoteRequest,
        @RequestPart("image") image: MultipartFile
    ) : ResponseEntity<BaseResponse<QuoteResponse>> {
        val response = quoteService.saveQuote(userSeq, quoteRequest, image)
        return ResponseEntity.ok(BaseResponse.success(response, "업로드 성공"))
    }

    @PostMapping("/generate")
    suspend fun generateImage(@RequestBody request: GenerateImageRequest): ResponseEntity<ByteArray> {
        val body = runCatching {
            quoteService.generateImage(request.content)
        }.onFailure {
            println(it)
        }.getOrNull()

        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType("image/webp"))
            .body(body)
    }

    @PostMapping
    fun getQuotes(
        @AuthenticationPrincipal userSeq: Long,
        @RequestBody request: QuotePageRequest,
        ): ResponseEntity<BaseResponse<CursorPage<QuoteResponse, QuoteCursor>>> {
        val cursor = request.cursor

        val data = quoteService.getQuotes(
            seed = request.seed,
            cursor = cursor,
            size = request.size.toLong(),
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
    fun getQuote(@PathVariable quoteSeq: Long, @AuthenticationPrincipal userSeq: Long?)
    : ResponseEntity<BaseResponse<QuoteResponse>> {
        val quote = quoteService.getQuote(quoteSeq, userSeq)
        return ResponseEntity.ok(BaseResponse.success(quote, "글림 조회 성공"))
    }
}

