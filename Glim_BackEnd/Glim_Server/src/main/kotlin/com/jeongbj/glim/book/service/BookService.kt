package com.jeongbj.glim.book.service

import com.jeongbj.glim.book.dto.BookResponse
import com.jeongbj.glim.book.mapper.toEntity
import com.jeongbj.glim.book.mapper.toResponse
import com.jeongbj.glim.book.repository.BookRepository
import com.jeongbj.glim.common.response.PagingResult
import com.jeongbj.glim.external.aladin.service.AladinService
import com.jeongbj.glim.external.aladin.type.ItemSearchQueryType
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BookService(
    private val bookRepository: BookRepository,
    private val aladinService: AladinService
) {

    @Transactional
    fun searchBook(query: String, queryType: ItemSearchQueryType, pageable: Pageable)
    : PagingResult<BookResponse> {
        val books = queryType.search(bookRepository, query, pageable)

        if(books.totalElements > 5) return PagingResult.from(books.map { it.toResponse() })

        val aladinBooks = aladinService.getAladinItemSearch(query, queryType)
            .map { it.toEntity() }
        val existingIsbn13s = bookRepository.findAllByIsbn13In(aladinBooks.map { it.isbn13 }
        ).map { it.isbn13 }.toSet()

        val newBooks = aladinBooks.filter { it.isbn13 !in existingIsbn13s }
        if(newBooks.isNotEmpty()) {
            bookRepository.saveAll(newBooks)
        }
        val saved = queryType.search(bookRepository, query, pageable)

        return PagingResult.from(saved.map { it.toResponse() })
    }

    fun searchBookByIsbn13(isbn13: String): BookResponse? {
        val book = bookRepository.findByIsbn13(isbn13)
        if(book != null) return book.toResponse()

        val newBook = aladinService.getAladinItemLookUp(isbn13) ?: return null

        return bookRepository.save(newBook[0].toEntity()).toResponse()
    }
}