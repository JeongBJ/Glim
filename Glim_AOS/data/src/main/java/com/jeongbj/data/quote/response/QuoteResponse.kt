package com.jeongbj.data.quote.response

import com.google.gson.annotations.SerializedName

data class QuoteResponse(
    @SerializedName("quoteSeq") val quoteSeq: Long,
    @SerializedName("imageUrl") val imageUrl: String,
    @SerializedName("content") val content: String,
    @SerializedName("numLikes") val numLikes: Long,
    @SerializedName("liked") val liked: Boolean,
    @SerializedName("user") val user: QuoteUserResponse,
    @SerializedName("book") val book: QuoteBookResponse
)

