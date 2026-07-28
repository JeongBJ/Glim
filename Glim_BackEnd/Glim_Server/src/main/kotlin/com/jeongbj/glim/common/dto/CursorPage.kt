package com.jeongbj.glim.common.dto

data class CursorPage<T, C>(
    val items: List<T>,
    val hasNext: Boolean,
    val nextCursor: C?,
    val seed: Long? = null
)