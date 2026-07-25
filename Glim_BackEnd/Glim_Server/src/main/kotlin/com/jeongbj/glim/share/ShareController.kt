package com.jeongbj.glim.share

import com.jeongbj.glim.common.dto.CursorPage
import com.jeongbj.glim.common.response.BaseResponse
import com.jeongbj.glim.info.dto.QuoteThumbnailResponse
import com.jeongbj.glim.quote.dto.QuoteCursor
import com.jeongbj.glim.quote.dto.QuotePageRequest
import com.jeongbj.glim.quote.service.QuoteService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/share")
class ShareController(
    private val quoteService: QuoteService
) {

    @GetMapping("/{quoteSeq}")
    fun shareQuote(
        @PathVariable quoteSeq: Long,
        model: Model
    ): String {

        val quote = quoteService.getQuote(quoteSeq, null)

        model.addAttribute("title", quote.content)
        model.addAttribute("image", quote.imageUrl)
        model.addAttribute("bookTitle", quote.book.title)

        return "share"
    }

    @PostMapping("/quotes")
    fun getLockScreenQuotes(
        @RequestBody request: QuotePageRequest
    )
    : ResponseEntity<BaseResponse<CursorPage<QuoteThumbnailResponse, QuoteCursor>?>> {
        val data = quoteService.getLockScreenQuotes(request)
        return ResponseEntity.ok(BaseResponse.success(data, "잠금화면 글림 조회 성공"))
    }
}