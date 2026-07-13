package com.jeongbj.glim.block.repository

import com.jeongbj.glim.block.entity.QBlockedQuote
import com.jeongbj.glim.common.dto.CursorPage
import com.jeongbj.glim.info.dto.QuoteThumbnailResponse
import com.jeongbj.glim.quote.entity.QQuote
import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class BlockedQuoteQueryRepository(
    private val queryFactory: JPAQueryFactory
) {
    private val quote = QQuote.quote
    private val blockedQuote = QBlockedQuote.blockedQuote

    fun getBlockedQuotes(
        userSeq: Long,
        cursor: Long?,
        size: Int
    )
    : CursorPage<QuoteThumbnailResponse, Long> {
        val result = queryFactory
            .select(
                Projections.constructor(
                    QuoteThumbnailResponse::class.java,
                    quote.quoteSeq,
                    quote.imageUrl
                )
            )
            .from(blockedQuote)
            .join(blockedQuote.quote, quote)
            .where(
                blockedQuote.user.userSeq.eq(userSeq),
                cursorCondition(cursor)
            )
            .orderBy(quote.quoteSeq.desc())
            .limit((size + 1).toLong())
            .fetch()
        
        val hasNext = result.size > size
        val content = if (hasNext) result.dropLast(1) else result
        val nextCursor = if (hasNext) content.last().quoteSeq else null
        
        return CursorPage(
            items = content,
            hasNext = hasNext,
            nextCursor = nextCursor
        )
    }
    
    private fun cursorCondition(cursor: Long?): BooleanExpression? {
        return cursor?.let { quote.quoteSeq.lt(it) }
    }
}
