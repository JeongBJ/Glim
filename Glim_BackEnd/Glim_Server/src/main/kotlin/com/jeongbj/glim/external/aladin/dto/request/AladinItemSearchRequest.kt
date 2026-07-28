package com.jeongbj.glim.external.aladin.dto.request

data class AladinItemSearchRequest(
    val queryType: String,
    val maxResults: Int = 100,
    val query: String
)