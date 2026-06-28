package com.jeongbj.glim.info.repository

import com.jeongbj.glim.info.dto.GlimContributionResponse
import com.jeongbj.glim.info.dto.InfoResponse
import com.jeongbj.glim.info.dto.InfoUserProjection
import com.jeongbj.glim.info.mapper.toResponse
import com.jeongbj.glim.like.entity.QLike
import com.jeongbj.glim.quote.entity.QQuote
import com.jeongbj.glim.user.dto.response.UserResponse
import com.jeongbj.glim.user.entity.QUser
import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.Expressions
import com.querydsl.jpa.JPAExpressions
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
class InfoQueryRepository(
    private val queryFactory: JPAQueryFactory
) {
    fun getInfo(userSeq: Long): InfoResponse {
        val quote = QQuote.quote
        val like = QLike.like
        val user = QUser.user

        val likeExpression = JPAExpressions
            .select(like.count().intValue())
            .from(like)
            .where(like.user.userSeq.eq(userSeq))

        val quoteExpression = JPAExpressions
            .select(quote.count().intValue())
            .from(quote)
            .where(quote.user.userSeq.eq(userSeq))

        val result = queryFactory
            .select(
                Projections.constructor(
                    InfoUserProjection::class.java,

                    Projections.constructor(
                        UserResponse::class.java,
                        Expressions.constant(""),
                        user.nickname,
                        user.imageUrl
                    ),

                    likeExpression,
                    quoteExpression
                )
            )
            .from(user)
            .where(user.userSeq.eq(userSeq))
            .fetchOne() ?: throw IllegalArgumentException("User Not Found")

        val contributions = getContributions(userSeq)

        return result.toResponse(contributions)
    }

    private fun getContributions(userSeq: Long): List<GlimContributionResponse> {
        val quote = QQuote.quote
        val start = LocalDate.now().minusYears(1)
        val date = Expressions.dateTemplate(
            LocalDate::class.java,
            "DATE({0})",
            quote.createdAt
        )
        val result = queryFactory
            .select(
                Projections.constructor(
                    GlimContributionResponse::class.java,
                    date,
                    quote.count().intValue()
                )
            )
            .from(quote)
            .where(
                quote.user.userSeq.eq(userSeq),
                quote.createdAt.goe(start.atStartOfDay())
            )
            .groupBy(date)
            .orderBy(date.asc())
            .fetch()

        return result
    }
}