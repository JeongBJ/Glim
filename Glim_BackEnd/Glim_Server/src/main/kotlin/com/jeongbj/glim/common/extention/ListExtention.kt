package com.jeongbj.glim.common.extention

import com.jeongbj.glim.common.response.PagingResult
import org.springframework.data.domain.Pageable
import kotlin.math.ceil

fun <T> List<T>.toPage(pageable: Pageable)
 : List<T> {
    val start = pageable.offset.toInt()
    if (start >= size) {
        return emptyList()
    }
    val end = minOf(
        start + pageable.pageSize,
        size
    )
    return subList(start, end)
}

fun <T> List<T>.toPagingResult(pageable: Pageable)
: PagingResult<T> {
    val start = pageable.offset.toInt()
    if (start >= size) {
        return PagingResult(
            page = pageable.pageNumber,
            totalPage = 0,
            totalElements = size.toLong(),
            hasNext = false,
            data = emptyList()
        )
    }
    val end = minOf(start + pageable.pageSize, size)
    val content = subList(start, end)
    val totalPage = ceil(size.toDouble() / pageable.pageSize).toInt()

    return PagingResult(
        page = pageable.pageNumber,
        totalPage = totalPage,
        totalElements = size.toLong(),
        hasNext = end < size,
        data = content
    )
}