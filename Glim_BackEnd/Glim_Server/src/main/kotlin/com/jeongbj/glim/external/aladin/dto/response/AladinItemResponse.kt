package com.jeongbj.glim.external.aladin.dto.response

import com.fasterxml.jackson.annotation.JsonProperty

data class AladinItemResponse(
    val title: String,
    @JsonProperty("link")
    val linkUrl: String,
    @JsonProperty("cover")
    val coverUrl: String,
    val author: String,
    val translator: String? = null,
    val isbn13: String,
    val description: String? = null,
    val pubDate: String,
    val priceSales: Int,
    val categoryId: Int,
    val categoryName: String,
    val publisher: String
)