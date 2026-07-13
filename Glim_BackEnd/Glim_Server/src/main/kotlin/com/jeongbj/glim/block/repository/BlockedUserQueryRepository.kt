package com.jeongbj.glim.block.repository

import com.jeongbj.glim.block.entity.QBlockedUser
import com.jeongbj.glim.common.dto.CursorPage
import com.jeongbj.glim.quote.entity.QQuote
import com.jeongbj.glim.user.dto.response.UserResponse
import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.Expressions
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class BlockedUserQueryRepository(
    private val queryFactory: JPAQueryFactory
) {
    private val quote = QQuote.quote
    private val blockedUser = QBlockedUser.blockedUser1

    fun getBlockedUsers(
        userSeq: Long,
        cursor: Long?,
        size: Int
    ): CursorPage<UserResponse, Long> {
        val cursorCondition = cursor?.let {
            blockedUser.blockedUser.userSeq.lt(it)
        }

        val result = queryFactory
            .select(
                Projections.constructor(
                    UserResponse::class.java,
                    blockedUser.blockedUser.userSeq,
                    Expressions.constant(""),
                    blockedUser.blockedUser.nickname,
                    blockedUser.blockedUser.imageUrl,
                )
            )
            .from(blockedUser)
            .where(
                blockedUser.user.userSeq.eq(userSeq),
                cursorCondition
            )
            .orderBy(blockedUser.createdAt.desc())
            .limit((size + 1).toLong())
            .fetch()

        val hasNext = result.size > size
        val items = if (hasNext) result.dropLast(1) else result
        val nextCursor = if (hasNext) items.last().userSeq else null

        return CursorPage(
            items = items,
            hasNext = hasNext,
            nextCursor = nextCursor
        )
    }
}