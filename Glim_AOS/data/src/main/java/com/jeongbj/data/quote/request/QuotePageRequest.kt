package com.jeongbj.data.quote.request

import com.jeongbj.domain.quote.model.QuoteCursor

data class QuotePageRequest(
    val seed: Long?,
    val cursor: QuoteCursor? = null,
    val size: Int = 20,
)
