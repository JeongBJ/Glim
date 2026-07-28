package com.jeongbj.glim.book.controller

import com.jeongbj.glim.book.dto.*
import com.jeongbj.glim.book.service.BookService
import com.jeongbj.glim.common.response.BaseResponse
import com.jeongbj.glim.common.response.PagingResult
import org.springframework.data.domain.PageRequest
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/book")
class BookController(
    private val bookService: BookService
) {

    @PostMapping("/search")
    fun searchBook(@RequestBody request: BookSearchRequest)
    : ResponseEntity<BaseResponse<PagingResult<BookResponse>>>{
        val pageable = PageRequest.of(
            request.page,
            request.size
        )
        val data = bookService.searchBook(request.query, request.queryType, pageable)
        return ResponseEntity.ok(BaseResponse.success(data, "책 정보 조회 성공"))
    }

    @GetMapping("/search/{isbn13}")
    fun searchBookByIsbn13(@AuthenticationPrincipal userSeq: Long, @PathVariable isbn13: String)
    : ResponseEntity<BaseResponse<BookDetailResponse?>> {
        val data = bookService.searchBookByIsbn13(isbn13, userSeq)
        return ResponseEntity.ok(BaseResponse.success(data, "성공"))
    }

    @GetMapping
    fun getHomeScreenData()
    :  ResponseEntity<BaseResponse<BookItemListResponse>> {
        val data = bookService.getHomeItemList()
        return ResponseEntity.ok(BaseResponse.success(data, "홈 화면 데이터 조회 성공"))
    }

    @GetMapping("/rank")
    fun getBookRank()
    : ResponseEntity<BaseResponse<List<BookRankResponse>>> {
        val data = bookService.getSearchRankTop10()
        return ResponseEntity.ok(BaseResponse.success(data, "검색 랭킹 조회 성공"))
    }
}