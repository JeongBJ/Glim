package com.jeongbj.glim.like.repository

import com.jeongbj.glim.like.entity.Like
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface LikeRepository: JpaRepository<Like, Long> {
    @Query("""
        select l.quote.quoteSeq
        from Like l
        where l.user.userSeq = :userSeq
        and l.quote.book.bookSeq = :bookSeq
    """)
    fun findLikedQuoteIds(userSeq: Long, bookSeq: Long): List<Long>

    fun findByUserUserSeqAndQuoteQuoteSeq(userSeq: Long, quoteSeq: Long): Like?

    fun deleteAllByUser_UserSeq(userSeq: Long)
}