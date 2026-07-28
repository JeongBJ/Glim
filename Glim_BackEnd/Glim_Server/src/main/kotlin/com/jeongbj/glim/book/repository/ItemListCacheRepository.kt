package com.jeongbj.glim.book.repository

import com.jeongbj.glim.book.dto.BookResponse
import com.jeongbj.glim.external.aladin.type.ItemListQueryType
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Repository
import tools.jackson.databind.ObjectMapper
import tools.jackson.module.kotlin.readValue
import java.time.Duration

@Repository
class ItemListCacheRepository (
    private val redisTemplate: StringRedisTemplate,
    private val objectMapper: ObjectMapper
) {
    fun get(queryType: ItemListQueryType): List<BookResponse>? {
        val value = redisTemplate.opsForValue().get(getKey(queryType)) ?: return null
        return objectMapper.readValue(value)
    }

    fun save(queryType: ItemListQueryType, value: List<BookResponse>) {
        redisTemplate.opsForValue().set(
            getKey(queryType),
            objectMapper.writeValueAsString(value),
            Duration.ofDays(2)
        )
    }

    private fun getKey(queryType: ItemListQueryType)
            : String {
        return "book:item:${queryType.value}"
    }
}