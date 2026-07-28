package com.jeongbj.glim.common.response

import org.springframework.data.domain.Page

data class PagingResult<T>(
    val page: Int,
    val totalPage: Int,
    val data: List<T>,
    val hasNext: Boolean,
    val totalElements: Long
) {
    companion object {

        fun <T : Any> from(page: Page<T>): PagingResult<T> {
            return PagingResult(
                page = page.number,
                totalPage = page.totalPages,
                totalElements = page.totalElements,
                hasNext = page.hasNext(),
                data = page.content
            )
        }
    }
}
