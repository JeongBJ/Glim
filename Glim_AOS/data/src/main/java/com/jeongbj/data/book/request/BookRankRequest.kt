package com.jeongbj.data.book.request

import kotlinx.serialization.Serializable

@Serializable
data class BookRankRequest(
    val rank: Int = 0,
    val title: String = "",
    val queryType: String
)
