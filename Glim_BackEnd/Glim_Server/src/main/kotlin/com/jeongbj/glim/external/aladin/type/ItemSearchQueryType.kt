package com.jeongbj.glim.external.aladin.type

import com.jeongbj.glim.external.aladin.dto.request.AladinItemSearchRequest

enum class ItemSearchQueryType(val value: String) {
    KEYWORD("Keyword"),
    TITLE("Title"),
    AUTHOR("Author"),
    PUBLISHER("Publisher");

    fun toRequest(
        query: String
    ): AladinItemSearchRequest {
        return AladinItemSearchRequest(
            query = query,
            queryType = value
        )
    }
}