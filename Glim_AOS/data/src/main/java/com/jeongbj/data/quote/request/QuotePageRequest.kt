package com.jeongbj.data.quote.request

import com.google.gson.annotations.SerializedName
import com.jeongbj.domain.quote.model.QuoteCursor

data class QuotePageRequest(
    @SerializedName("seed") val seed: Long?,
    @SerializedName("cursor") val cursor: QuoteCursor? = null,
    @SerializedName("size") val size: Int = 20,
)
