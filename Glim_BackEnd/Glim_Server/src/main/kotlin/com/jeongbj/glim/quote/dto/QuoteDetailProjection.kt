package com.jeongbj.glim.quote.dto

data class QuoteDetailProjection(
    val quoteSeq: Long,
    val imageUrl: String,
    val content: String,
    val numViews: Long,
    val numLikes: Long,
    val liked: Boolean,

    val userSeq: Long,
    val nickname: String,
    val profileImageUrl: String?,

    val bookSeq: Long,
    val title: String,
    val coverUrl: String,
    val author: String,
    val isbn13: String
)