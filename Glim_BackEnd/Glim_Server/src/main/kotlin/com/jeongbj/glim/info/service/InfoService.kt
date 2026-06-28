package com.jeongbj.glim.info.service

import com.jeongbj.glim.common.dto.CursorPage
import com.jeongbj.glim.info.dto.InfoResponse
import com.jeongbj.glim.info.dto.QuoteThumbnailResponse
import com.jeongbj.glim.info.repository.InfoQueryRepository
import com.jeongbj.glim.quote.repository.QuoteQueryRepository
import org.springframework.stereotype.Service


@Service
class InfoService(
    private val infoQueryRepository: InfoQueryRepository,
    private val quoteQueryRepository: QuoteQueryRepository
) {
    fun getInfo(userSeq: Long): InfoResponse {
        val result = infoQueryRepository.getInfo(userSeq)
        return result
    }

    fun getUserQuotes(userSeq: Long, cursor: Long?, size: Int): CursorPage<QuoteThumbnailResponse, Long> {
        return quoteQueryRepository.getUserQuotes(userSeq, cursor, size)
    }

    fun getLikedQuotes(userSeq: Long, cursor: Long?, size: Int): CursorPage<QuoteThumbnailResponse, Long> {
        return quoteQueryRepository.getLikedQuotes(userSeq, cursor, size)
    }
}