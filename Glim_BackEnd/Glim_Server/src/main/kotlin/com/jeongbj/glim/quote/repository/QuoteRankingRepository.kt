package com.jeongbj.glim.quote.repository

import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Repository
import tools.jackson.databind.ObjectMapper

@Repository
class QuoteRankingRepository(
    private val redisTemplate: StringRedisTemplate,
    private val objectMapper: ObjectMapper
) {

    fun getTop20(): List<Long> {
        return redisTemplate.opsForZSet().reverseRangeWithScores(
            getKey(),
            0,
            20
        )?.mapNotNull { tuple ->
            tuple.value?.toLong()
        }?: emptyList()
    }

    fun increaseView(quoteSeq: Long) {
        redisTemplate.opsForZSet()
            .incrementScore(
                getKey(),
                quoteSeq.toString(),
                1.0
            )
    }

    fun increaseLike(quoteSeq: Long) {
        redisTemplate.opsForZSet()
            .incrementScore(
                getKey(),
                quoteSeq.toString(),
                10.0
            )
    }

    fun decreaseLike(quoteSeq: Long) {
        redisTemplate.opsForZSet()
            .incrementScore(
                getKey(),
                quoteSeq.toString(),
                -10.0
            )
    }

    private fun getKey()
            : String {
        return "quote:rank"
    }
}