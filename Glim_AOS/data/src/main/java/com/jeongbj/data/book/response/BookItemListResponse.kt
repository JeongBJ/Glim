package com.jeongbj.data.book.response

import com.google.gson.annotations.SerializedName

data class BookItemListResponse(
    @SerializedName("todayQuotes") val todayQuotes: List<QuoteRankResponse>,
    @SerializedName("bestSeller") val bestSeller: List<BookResponse>,
    @SerializedName("newSpecial") val newSpecial: List<BookResponse>,
    @SerializedName("editorChoice") val editorChoice: List<BookResponse>
)
