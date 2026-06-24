package com.jeongbj.glim.book.dto

data class BookRankRequest(
    var rank: Int = 0,
    val title: String,
    val queryType: String
)