package com.jeongbj.glim.external.aladin.dto.request

data class AladinItemLookUpRequest(
    val itemId: String,
    val itemIdType: String = "ISBN13"
)