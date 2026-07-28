package com.jeongbj.glim.external.aladin.dto.request

import com.fasterxml.jackson.annotation.JsonProperty

data class AladinItemListRequest(
    @JsonProperty("QueryType")
    val queryType: String,
    @JsonProperty("MaxResults")
    val maxResults: Int = 100,
    @JsonProperty("CategoryId")
    val categoryId: Int? = null
)