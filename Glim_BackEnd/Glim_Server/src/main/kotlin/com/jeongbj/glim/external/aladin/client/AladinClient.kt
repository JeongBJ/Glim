package com.jeongbj.glim.external.aladin.client

import com.jeongbj.glim.external.aladin.dto.request.AladinItemListRequest
import com.jeongbj.glim.external.aladin.dto.request.AladinItemLookUpRequest
import com.jeongbj.glim.external.aladin.dto.request.AladinItemSearchRequest
import com.jeongbj.glim.external.aladin.dto.response.AladinItemResponse
import com.jeongbj.glim.external.aladin.dto.response.AladinResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.cloud.openfeign.SpringQueryMap
import org.springframework.web.bind.annotation.GetMapping

@FeignClient(
    name = "aladinClient",
    url = $$"${aladin.base-url}"
)
interface AladinClient {

    @GetMapping("/ttb/api/ItemList.aspx")
    fun aladinItemList(
        @SpringQueryMap request: AladinItemListRequest
    ): AladinResponse

    @GetMapping("/ttb/api/ItemSearch.aspx")
    fun aladinItemSearch(
        @SpringQueryMap request: AladinItemSearchRequest
    ): AladinResponse

    @GetMapping("/ttb/api/ItemLookUp.aspx")
    fun aladinItemLookUp(
        @SpringQueryMap request: AladinItemLookUpRequest
    ): AladinResponse
}