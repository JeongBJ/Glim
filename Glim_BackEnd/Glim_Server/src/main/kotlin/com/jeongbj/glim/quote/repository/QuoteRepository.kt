package com.jeongbj.glim.quote.repository

import com.jeongbj.glim.quote.entity.Quote
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface QuoteRepository : JpaRepository<Quote, Long> {
    fun findByBookBookSeqOrderByNumLikesDescNumViewsDesc(bookSeq: Long): List<Quote>

    @Modifying
    @Query(
        """
    update Quote q
    set q.numLikes = q.numLikes + 1
    where q.quoteSeq = :quoteSeq
    """)
    fun increaseLikes(@Param("quoteSeq") quoteSeq: Long)


    @Modifying
    @Query(
        """
    update Quote q
    set q.numLikes = q.numLikes - 1
    where q.quoteSeq = :quoteSeq
    """)
    fun decreaseLikes(@Param("quoteSeq") quoteSeq: Long)
}

