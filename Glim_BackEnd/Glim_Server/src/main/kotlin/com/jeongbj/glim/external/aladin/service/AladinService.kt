package com.jeongbj.glim.external.aladin.service

import com.jeongbj.glim.external.aladin.client.AladinClient
import com.jeongbj.glim.external.aladin.dto.request.AladinItemLookUpRequest
import com.jeongbj.glim.external.aladin.dto.response.AladinItemResponse
import com.jeongbj.glim.external.aladin.type.ItemListQueryType
import com.jeongbj.glim.external.aladin.type.ItemSearchQueryType
import org.springframework.stereotype.Service

@Service
class AladinService(
    private val aladinClient: AladinClient
) {

    fun getAladinItemList(queryType: ItemListQueryType): List<AladinItemResponse> =
        aladinClient.aladinItemList(queryType.toRequest()).item

    fun getAladinItemSearch(query: String, queryType: ItemSearchQueryType): List<AladinItemResponse> =
        aladinClient.aladinItemSearch(queryType.toRequest(query)).item

    fun getAladinItemLookUp(isbn13: String): List<AladinItemResponse> =
        aladinClient.aladinItemLookUp(AladinItemLookUpRequest(itemId = isbn13)).item
}