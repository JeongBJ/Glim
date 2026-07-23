package com.jeongbj.data.book.request

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class BookRankRequest(
    @SerializedName("rank") val rank: Int = 0,
    @SerializedName("title") val title: String = "",
    @SerializedName("queryType") val queryType: String
)
