package com.jeongbj.glim.share

import com.jeongbj.glim.quote.service.QuoteService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping

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
}