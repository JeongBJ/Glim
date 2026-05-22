package com.jeongbj.glim.book.controller

import com.jeongbj.glim.book.dto.BookResponse
import com.jeongbj.glim.book.dto.BookSearchRequest
import com.jeongbj.glim.book.service.BookService
import com.jeongbj.glim.common.response.BaseResponse
import com.jeongbj.glim.common.response.PagingResult
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/book")
class BookController(
    private val bookService: BookService
) {

    @PostMapping("/search")
    fun searchBook(@RequestBody request: BookSearchRequest, pageable: Pageable)
    : ResponseEntity<BaseResponse<PagingResult<BookResponse>>>{
        val data = bookService.searchBook(request.query, request.queryType, pageable)
        return ResponseEntity.ok(BaseResponse.success(data, "책 정보 조회 성공"))
    }

    @GetMapping("/search/{isbn13}")
    fun searchBookByIsbn13(@PathVariable isbn13: String)
    : ResponseEntity<BaseResponse<BookResponse?>> {
        val data = bookService.searchBookByIsbn13(isbn13)
        return ResponseEntity.ok(BaseResponse.success(data, "성공"))
    }
}