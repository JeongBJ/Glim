package com.jeongbj.data.book.response

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class BookRankResponse(
    @SerializedName("rank") var rank: Int = 0,
    @SerializedName("title") val title: String,
    @SerializedName("queryType") val queryType: String
)
