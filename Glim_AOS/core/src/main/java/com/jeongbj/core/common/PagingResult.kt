package com.jeongbj.core.common


data class PagingResult<T>(
    val page: Int,
    val totalPage: Int,
    val data: List<T>,
    val hasNext: Boolean,
    val totalElements: Long
)
