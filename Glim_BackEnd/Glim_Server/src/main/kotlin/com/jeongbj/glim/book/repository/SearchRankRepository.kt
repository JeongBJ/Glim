package com.jeongbj.glim.book.repository

import com.jeongbj.glim.book.dto.BookRankResponse
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Repository
import tools.jackson.databind.ObjectMapper
import java.time.Duration
import java.time.YearMonth

@Repository
class SearchRankRepository(
    private val redisTemplate: StringRedisTemplate,
    private val objectMapper: ObjectMapper
) {
    fun getTop10(): List<BookRankResponse> {
        return redisTemplate.opsForZSet().reverseRangeWithScores(
            getKey(),
            0,
            9
        )?.mapIndexed { index, tuple ->
            BookRankResponse(
                rank = index + 1,
                title = tuple.value.orEmpty(),
                queryType = "도서"
            )
        }?: emptyList()
    }

    fun increase(query: String) {
        redisTemplate.opsForZSet().incrementScore(
            getKey(),
            query.lowercase().trim(),
            1.0,
        )
        redisTemplate.expire(getKey(), Duration.ofDays(32))
    }

    private fun getKey()
            : String {
        return "search:rank:${YearMonth.now()}"
    }
}