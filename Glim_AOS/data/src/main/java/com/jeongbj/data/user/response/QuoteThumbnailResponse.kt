package com.jeongbj.data.user.response

import com.google.gson.annotations.SerializedName

data class QuoteThumbnailResponse(
    @SerializedName("quoteSeq") val quoteSeq: Long,
    @SerializedName("imageUrl") val imageUrl: String?
)
