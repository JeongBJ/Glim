package com.jeongbj.glim.quote.service

import com.jeongbj.glim.book.mapper.toQuoteResponse
import com.jeongbj.glim.book.repository.BookRepository
import com.jeongbj.glim.external.ai.gemini.service.GeminiService
import com.jeongbj.glim.external.ai.pollination.service.PollinationService
import com.jeongbj.glim.infra.bucket.BucketService
import com.jeongbj.glim.quote.dto.QuoteRequest
import com.jeongbj.glim.quote.dto.QuoteResponse
import com.jeongbj.glim.quote.entity.Quote
import com.jeongbj.glim.quote.mapper.toResponse
import com.jeongbj.glim.quote.repository.QuoteRepository
import com.jeongbj.glim.user.mapper.toQuoteResponse
import com.jeongbj.glim.user.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class QuoteService(
    private val quoteRepository: QuoteRepository,
    private val bookRepository: BookRepository,
    private val bucketService: BucketService,
    private val userRepository: UserRepository,
    private val pollinationService: PollinationService,
    private val geminiService: GeminiService
) {

    fun generateImage(content: String): ByteArray {
        val prompt = geminiService.generateText(content)
        println(prompt)
        return pollinationService.generateImage(prompt)
    }

    fun saveQuote(userSeq: Long, request: QuoteRequest, multipartFile: MultipartFile)
    : QuoteResponse {
        val book = bookRepository.findByIsbn13(request.isbn13)?: throw IllegalArgumentException("Book not found")
        val user = userRepository.findById(userSeq).orElseThrow { IllegalArgumentException("User not found") }
        val imageUrl = bucketService.uploadImage(multipartFile, BucketService.IMAGE)
        val saved = quoteRepository.save(Quote(
            book = book,
            imageUrl = imageUrl,
            content = request.content,
            user = user
        ))

        return saved.toResponse(
            liked = false,
            user = user.toQuoteResponse(),
            book = book.toQuoteResponse()
        )
    }

}