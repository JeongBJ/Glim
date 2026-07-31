package com.jeongbj.glim.block.repository

import com.jeongbj.glim.block.entity.BlockedQuote
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface BlockedQuoteRepository: JpaRepository<BlockedQuote, Long> {
    fun deleteByUser_UserSeqAndQuote_QuoteSeq(userSeq: Long, quoteSeq: Long): Optional<BlockedQuote>
    fun deleteAllByUser_UserSeq(userSeq: Long)
}