package com.jeongbj.glim.book.repository

import com.jeongbj.glim.book.entity.Book
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface BookRepository: JpaRepository<Book, Long> {
    fun findByIsbn13(isbn13: String): Book?

    fun findByTitleContainingIgnoreCase(
        title: String,
        pageable: Pageable
    ): Page<Book>

    fun findByAuthorContainingIgnoreCase(
        author: String,
        pageable: Pageable
    ): Page<Book>

    fun findByPublisherContainingIgnoreCase(
        publisher: String,
        pageable: Pageable
    ): Page<Book>

    fun findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(
        title: String,
        author: String,
        pageable: Pageable
    ): Page<Book>

    fun findAllByIsbn13In(map: List<String>): List<Book>
}