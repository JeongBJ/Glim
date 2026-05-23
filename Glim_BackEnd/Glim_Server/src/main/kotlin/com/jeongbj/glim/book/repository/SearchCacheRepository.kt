package com.jeongbj.glim.book.repository

import com.jeongbj.glim.book.dto.BookResponse
import com.jeongbj.glim.external.aladin.type.ItemSearchQueryType
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Repository
import tools.jackson.databind.ObjectMapper
import tools.jackson.module.kotlin.readValue
import java.time.Duration

@Repository
class SearchCacheRepository(
    private val redisTemplate: StringRedisTemplate,
    private val objectMapper: ObjectMapper
) {
    fun get(query: String, queryType: ItemSearchQueryType): List<BookResponse>? {
        val value = redisTemplate.opsForValue().get(getKey(query, queryType)) ?: return null
        return objectMapper.readValue(value)
    }

    fun save(query: String, queryType: ItemSearchQueryType, value: List<BookResponse>) {
        redisTemplate.opsForValue().set(
            getKey(query, queryType),
            objectMapper.writeValueAsString(value),
            Duration.ofDays(7)
        )
    }

    private fun getKey(query: String, queryType: ItemSearchQueryType)
    : String {
        return "book:search:${query.lowercase().trim()}:${queryType.value}"
    }
}