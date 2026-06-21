package com.jeongbj.data.book.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.jeongbj.data.book.mapper.toDomain
import com.jeongbj.data.book.request.BookRankRequest
import com.jeongbj.data.book.response.BookRankResponse
import com.jeongbj.domain.book.model.BookRank
import com.jeongbj.domain.book.model.QueryType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json
import javax.inject.Inject

class RecentQueryLocalDataSource @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    fun get(): Flow<List<BookRank>> {
        return dataStore.data.map { preferences ->
            preferences[PREF_KEY]?.let { json ->
                decodeRecentQuery(json).map { it.toDomain() }
            } ?: emptyList()
        }
    }

    suspend fun save(query: String, queryType: QueryType) {
        dataStore.edit { preferences ->
            val current = preferences[PREF_KEY]?.let(::decodeRecentQuery)
                .orEmpty()
            val updated = buildList {
                add(BookRankRequest(
                    title = query,
                    queryType = queryType.displayName
                ))
                addAll(current.filterNot {
                    it.title == query && it.queryType == queryType.displayName
                })
            }.take(MAX_COUNT)

            preferences[PREF_KEY] = Json.encodeToString(updated)
        }
    }

    suspend fun clear() {
        dataStore.edit {
            it.remove(PREF_KEY)
        }
    }

    private fun decodeRecentQuery(json: String): List<BookRankResponse> {
        return runCatching {
            Json.decodeFromString<List<BookRankResponse>>(json)
        }.getOrDefault(emptyList())
    }

    companion object {
        private const val MAX_COUNT = 10

        private val PREF_KEY =
            stringPreferencesKey("recent_query")
    }
}