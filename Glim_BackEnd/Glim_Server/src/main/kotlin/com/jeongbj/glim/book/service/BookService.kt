package com.jeongbj.glim.book.service

import com.jeongbj.glim.book.dto.BookItemListResponse
import com.jeongbj.glim.book.dto.BookResponse
import com.jeongbj.glim.book.entity.Book
import com.jeongbj.glim.book.mapper.toEntity
import com.jeongbj.glim.book.mapper.toResponse
import com.jeongbj.glim.book.repository.BookRepository
import com.jeongbj.glim.book.repository.ItemListCacheRepository
import com.jeongbj.glim.book.repository.SearchCacheRepository
import com.jeongbj.glim.common.extention.toPagingResult
import com.jeongbj.glim.common.response.PagingResult
import com.jeongbj.glim.external.aladin.service.AladinService
import com.jeongbj.glim.external.aladin.type.ItemListQueryType
import com.jeongbj.glim.external.aladin.type.ItemSearchQueryType
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BookService(
    private val bookRepository: BookRepository,
    private val searchCacheRepository: SearchCacheRepository,
    private val itemListCacheRepository: ItemListCacheRepository,
    private val aladinService: AladinService
) {

    @Transactional
    fun searchBook(query: String, queryType: ItemSearchQueryType, pageable: Pageable)
    : PagingResult<BookResponse> {
        searchCacheRepository.get(query, queryType)?.let { cached ->
            return cached.toPagingResult(pageable)
        }

        val aladinBooks = aladinService.getAladinItemSearch(query, queryType)
            .map { it.toEntity() }
        saveNewBooks(aladinBooks)
        val response = aladinBooks.map { it.toResponse() }
        searchCacheRepository.save(query, queryType, response)
        return response.toPagingResult(pageable)
    }

    fun getAladinItemList(): BookItemListResponse {
        val bestSeller = getItemList(ItemListQueryType.BEST_SELLER)
        val editorChoice = getItemList(ItemListQueryType.EDITOR_CHOICE)
        val newSpecial = getItemList(ItemListQueryType.NEW_SPECIAL)

        return BookItemListResponse(
            bestSeller = bestSeller,
            editorChoice = editorChoice,
            newSpecial = newSpecial
        )
    }

    fun searchBookByIsbn13(isbn13: String): BookResponse? {
        val book = bookRepository.findByIsbn13(isbn13)
        if(book != null) return book.toResponse()

        val newBook = aladinService.getAladinItemLookUp(isbn13) ?: return null

        return bookRepository.save(newBook.toEntity()).toResponse()
    }

    private fun saveNewBooks(books: List<Book>): List<Book> {
        val existingIsbn13s = bookRepository.findAllByIsbn13In(books.map { it.isbn13 })
            .map { it.isbn13 }.toSet()
        val newBooks = books.filter { it.isbn13 !in existingIsbn13s }
        if (newBooks.isNotEmpty()) bookRepository.saveAll(newBooks)
        return books
    }

    private fun getItemList(type: ItemListQueryType): List<BookResponse> {
        itemListCacheRepository.get(type)?.let {
            return it
        }
        val newBooks = aladinService.getAladinItemList(type)
        val entities = newBooks.map { it.toEntity() }
        saveNewBooks(entities)
        val response = entities.map { it.toResponse() }
        itemListCacheRepository.save(type, response)
        return response
    }
}