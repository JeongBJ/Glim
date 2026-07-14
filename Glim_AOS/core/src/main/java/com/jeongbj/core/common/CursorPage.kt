package com.jeongbj.core.common

data class CursorPage<T, C>(
    val items: List<T>,
    val hasNext: Boolean,
    val nextCursor: C?,
    val seed: Long? = null
)

inline fun <T, R, C> CursorPage<T, C>.map(
    transform: (T) -> R
): CursorPage<R, C> {
    return CursorPage(
        items = items.map(transform),
        hasNext = hasNext,
        nextCursor = nextCursor
    )
}