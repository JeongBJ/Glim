package com.jeongbj.data.book.response

import com.google.gson.annotations.SerializedName

data class BookDetailResponse(
    @SerializedName("title") val title: String,
    @SerializedName("coverUrl") val coverUrl: String,
    @SerializedName("linkUrl") val linkUrl: String? = null,
    @SerializedName("author") val author: String,
    @SerializedName("translator") val translator: String? = null,
    @SerializedName("isbn13") val isbn13: String,
    @SerializedName("description") val description: String? = null,
    @SerializedName("pubDate") val pubDate: String? = null,
    @SerializedName("priceSales") val priceSales: Int? = null,
    @SerializedName("publisher") val publisher: String? = null,
    @SerializedName("category") val category: String? = null,
    @SerializedName("quotes") val quotes: List<QuoteSummaryResponse>? = listOf()
)
