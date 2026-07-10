package com.jeongbj.glim.like.service

import com.jeongbj.glim.external.firebase.service.PushService
import com.jeongbj.glim.like.entity.Like
import com.jeongbj.glim.like.repository.LikeRepository
import com.jeongbj.glim.quote.repository.QuoteRankingRepository
import com.jeongbj.glim.quote.repository.QuoteRepository
import com.jeongbj.glim.user.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class LikeService(
    private val quoteRepository: QuoteRepository,
    private val likeRepository: LikeRepository,
    private val userRepository: UserRepository,
    private val quoteRankingRepository: QuoteRankingRepository,
    private val pushService: PushService
) {

    fun toggleLikeQuote(userSeq: Long, quoteSeq: Long): Boolean {
        val quote = quoteRepository.findById(quoteSeq).orElseThrow()
        val like = likeRepository.findByUserUserSeqAndQuoteQuoteSeq(userSeq, quoteSeq)

        if(like != null) {
            likeRepository.delete(like)
            quote.decreaseLikes()
            quoteRankingRepository.decreaseLike(quoteSeq)
            return false
        }

        val user = userRepository.getReferenceById(userSeq)
        likeRepository.save(Like(user = user, quote = quote))
        quote.increaseLikes()
        quoteRankingRepository.increaseLike(quoteSeq)
        pushService.notifyQuoteLiked(userSeq, quote)

        return true
    }
}