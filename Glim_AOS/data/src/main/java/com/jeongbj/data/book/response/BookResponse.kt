package com.jeongbj.data.book.response

import com.google.gson.annotations.SerializedName

data class BookResponse(
    @SerializedName("title") val title: String,
    @SerializedName("coverUrl") val coverUrl: String,
    @SerializedName("author") val author: String,
    @SerializedName("isbn13") val isbn13: String,
    @SerializedName("description") val description: String?,
    @SerializedName("pubDate") val pubDate: String,
    @SerializedName("publisher") val publisher: String?
)
