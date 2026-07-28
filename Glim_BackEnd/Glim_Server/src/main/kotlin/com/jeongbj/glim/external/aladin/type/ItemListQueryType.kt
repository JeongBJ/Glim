package com.jeongbj.glim.external.aladin.type

import com.jeongbj.glim.external.aladin.dto.request.AladinItemListRequest

enum class ItemListQueryType(
    val value: String
) {

    NEW_SPECIAL("ItemNewSpecial"),
    BEST_SELLER("BestSeller"),
    EDITOR_CHOICE("ItemEditorChoice");

    fun toRequest(): AladinItemListRequest {
        return when (this) {

            NEW_SPECIAL ->
                AladinItemListRequest(
                    queryType = value
                )

            BEST_SELLER ->
                AladinItemListRequest(
                    queryType = value
                )

            EDITOR_CHOICE ->
                AladinItemListRequest(
                    queryType = value,
                    categoryId = 1
                )
        }
    }
}