package com.jeongbj.data.book.response

import kotlinx.serialization.Serializable

@Serializable
data class BookRankResponse(
    var rank: Int = 0,
    val title: String,
    val queryType: String
)
