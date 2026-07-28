package com.jeongbj.glim.block.service

import com.jeongbj.glim.block.entity.BlockedQuote
import com.jeongbj.glim.block.entity.BlockedUser
import com.jeongbj.glim.block.repository.BlockedQuoteQueryRepository
import com.jeongbj.glim.block.repository.BlockedQuoteRepository
import com.jeongbj.glim.block.repository.BlockedUserQueryRepository
import com.jeongbj.glim.block.repository.BlockedUserRepository
import com.jeongbj.glim.common.dto.CursorPage
import com.jeongbj.glim.info.dto.QuoteThumbnailResponse
import com.jeongbj.glim.quote.repository.QuoteRepository
import com.jeongbj.glim.user.dto.response.UserResponse
import com.jeongbj.glim.user.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class BlockService(
    private val quoteRepository: QuoteRepository,
    private val userRepository: UserRepository,
    private val blockedQuoteRepository: BlockedQuoteRepository,
    private val blockedUserRepository: BlockedUserRepository,
    private val blockedQuoteQueryRepository: BlockedQuoteQueryRepository,
    private val blockedUserQueryRepository: BlockedUserQueryRepository
) {
    fun blockQuote(userSeq: Long, quoteSeq: Long) {
        val user = userRepository.findById(userSeq).orElseThrow()
        val quote = quoteRepository.findById(quoteSeq).orElseThrow()
        blockedQuoteRepository.save(
            BlockedQuote(
                user = user,
                quote = quote
            )
        )
    }

    fun blockUser(userSeq: Long, blockedUserSeq: Long) {
        val user = userRepository.findById(userSeq).orElseThrow()
        val blockedUser = userRepository.findById(blockedUserSeq).orElseThrow()
        blockedUserRepository.save(BlockedUser(
            user = user,
            blockedUser = blockedUser
        ))
    }

    fun unblockQuote(userSeq: Long, quoteSeq: Long) {
        blockedQuoteRepository.deleteByUser_UserSeqAndQuote_QuoteSeq(userSeq, quoteSeq).orElseThrow()
    }

    fun unblockUser(userSeq: Long, blockedUserSeq: Long) {
        blockedUserRepository.deleteByUser_UserSeqAndBlockedUser_UserSeq(userSeq, blockedUserSeq).orElseThrow()
    }

    fun getBlockedQuotes(userSeq: Long, cursor: Long?, size: Int)
    : CursorPage<QuoteThumbnailResponse, Long> {
        return blockedQuoteQueryRepository.getBlockedQuotes(userSeq, cursor, size)
    }

    fun getBlockedUsers(userSeq: Long, cursor: Long?, size: Int)
    : CursorPage<UserResponse, Long> {
        return blockedUserQueryRepository.getBlockedUsers(userSeq, cursor, size)
    }

}