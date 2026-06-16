package com.jeongbj.glim.quote.repository

import com.jeongbj.glim.book.entity.QBook
import com.jeongbj.glim.common.dto.CursorPage
import com.jeongbj.glim.like.entity.QLike
import com.jeongbj.glim.quote.dto.QuoteCursor
import com.jeongbj.glim.quote.dto.QuoteDetailProjection
import com.jeongbj.glim.quote.dto.QuoteProjection
import com.jeongbj.glim.quote.dto.QuoteResponse
import com.jeongbj.glim.quote.entity.QQuote
import com.jeongbj.glim.quote.mapper.toQuoteResponse
import com.jeongbj.glim.user.entity.QUser
import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.Expressions
import com.querydsl.jpa.JPAExpressions
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class QuoteQueryRepository(
    private val queryFactory: JPAQueryFactory,
){

    fun getQuotes(
        seed: Long,
        cursor: QuoteCursor?,
        size: Long,
        userSeq: Long
    ): CursorPage<QuoteResponse, QuoteCursor> {
        val quote = QQuote.quote
        val user = QUser.user
        val book = QBook.book
        val like = QLike.like

        val randomScore = Expressions.numberTemplate(
            Long::class.java,
            """
                mod(
                    function('hashtext', concat({0}, {1})),
                    100
                ) + 100
                """.trimIndent(),
            quote.quoteSeq,
            seed.toString()
        )

        val score = Expressions.numberTemplate(
            Long::class.java,
            """
                ({0} * 100)
                + ({1} * 10)
                + {2}
                """.trimIndent(),
            quote.numLikes,
            quote.numViews,
            randomScore
        )

        val likeExpression = JPAExpressions
            .selectOne()
            .from(like)
            .where(
                like.user.userSeq.eq(userSeq),
                like.quote.quoteSeq.eq(quote.quoteSeq)
            )
            .exists()

        val cursorCondition = cursor?.let {
            score.lt(it.score)
                .or(
                    score.eq(it.score)
                        .and(
                            quote.quoteSeq.lt(it.quoteSeq)
                        )
                )
        }

        val results = queryFactory
            .select(
                Projections.constructor(
                    QuoteProjection::class.java,
                    quote.quoteSeq,
                    quote.imageUrl,
                    quote.content,
                    quote.numViews,
                    quote.numLikes,
                    likeExpression,

                    user.userSeq,
                    user.nickname,
                    user.imageUrl,

                    book.bookSeq,
                    book.title,
                    book.coverUrl,
                    book.author,
                    book.isbn13,

                    score
                )
            )
            .from(quote)
            .join(quote.user, user)
            .join(quote.book, book)
            .where(
                cursorCondition
            )
            .orderBy(
                score.desc(),
                quote.quoteSeq.desc()
            )
            .limit(size + 1)
            .fetch()

        val hasNext = results.size > size

        val items = if (hasNext) {
            results.dropLast(1)
        } else {
            results
        }

        val nextCursor = if (hasNext) {
            items.lastOrNull()?.let {
                QuoteCursor(
                    score = it.score,
                    quoteSeq = it.quoteSeq
                )
            }
        } else {
            null
        }
        return CursorPage(
            items = items.map { it.toQuoteResponse() },
            hasNext = hasNext,
            nextCursor = nextCursor,
            seed = seed
        )
    }


    fun getQuote(quoteSeq: Long, userSeq: Long): QuoteResponse? {
        val quote = QQuote.quote
        val user = QUser.user
        val book = QBook.book
        val like = QLike.like

        val likeExpression = JPAExpressions
            .selectOne()
            .from(like)
            .where(
                like.user.userSeq.eq(userSeq),
                like.quote.quoteSeq.eq(quote.quoteSeq)
            )
            .exists()

        val result = queryFactory
            .select(
                Projections.constructor(
                    QuoteDetailProjection::class.java,
                    quote.quoteSeq,
                    quote.imageUrl,
                    quote.content,
                    quote.numViews,
                    quote.numLikes,
                    likeExpression,

                    user.userSeq,
                    user.nickname,
                    user.imageUrl,

                    book.bookSeq,
                    book.title,
                    book.coverUrl,
                    book.author,
                    book.isbn13,
                )
            )
            .from(quote)
            .join(quote.user, user)
            .join(quote.book, book)
            .where(
                quote.quoteSeq.eq(quoteSeq)
            )
            .fetchOne()

        return result?.toQuoteResponse()
    }


}