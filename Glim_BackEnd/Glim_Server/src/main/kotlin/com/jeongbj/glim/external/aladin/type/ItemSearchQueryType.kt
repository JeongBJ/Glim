package com.jeongbj.glim.external.aladin.type

import com.jeongbj.glim.book.entity.Book
import com.jeongbj.glim.book.repository.BookRepository
import com.jeongbj.glim.external.aladin.dto.request.AladinItemSearchRequest
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

enum class ItemSearchQueryType(val value: String) {
    KEYWORD("Keyword"),
    TITLE("Title"),
    AUTHOR("Author"),
    PUBLISHER("Publisher");

    fun toRequest(
        query: String
    ): AladinItemSearchRequest {
        return AladinItemSearchRequest(
            query = query,
            queryType = value
        )
    }

    fun search(
        repository: BookRepository,
        query: String,
        pageable: Pageable
    ): Page<Book> {
        return when (this) {
            KEYWORD ->
                repository.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(
                        query,
                        query,
                        pageable
                    )

            TITLE ->
                repository.findByTitleContainingIgnoreCase(
                        query,
                        pageable
                    )

            AUTHOR ->
                repository.findByAuthorContainingIgnoreCase(
                        query,
                        pageable
                    )

            PUBLISHER ->
                repository.findByPublisherContainingIgnoreCase(
                        query,
                        pageable
                    )
        }
    }
}