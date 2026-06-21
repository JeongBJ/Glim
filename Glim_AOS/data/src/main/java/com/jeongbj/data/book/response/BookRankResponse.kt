package com.jeongbj.data.book.response

import kotlinx.serialization.Serializable

@Serializable
data class BookRankResponse(
    val rank: Int = 0,
    val title: String,
    val queryType: String
)
