package com.jeongbj.core.common

data class CursorPage<T, C>(
    val items: List<T>,
    val hasNext: Boolean,
    val nextCursor: C?,
    val seed: Long
)