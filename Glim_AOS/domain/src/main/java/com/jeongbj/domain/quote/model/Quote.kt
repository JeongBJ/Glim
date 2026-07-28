package com.jeongbj.domain.quote.model

import com.jeongbj.domain.book.model.Book
import com.jeongbj.domain.user.model.User

data class Quote(
    val quoteSeq: Long,
    val imageUrl: String,
    val content: String,
    val numLikes: Long,
    val liked: Boolean,
    val user: User,
    val book: Book
)
