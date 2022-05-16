package com.ich.reciptopia.data.remote.dto

import com.ich.reciptopia.domain.model.SearchHistory

data class SearchHistoryDto(
    var id: Long? = null,
    var ownerId: Long? = null,
    var ingredientNames: List<String?> = emptyList()
)

fun SearchHistoryDto.toSearchHistory(): SearchHistory{
    return SearchHistory(
        id = id,
        ownerId = ownerId,
        ingredientNames = ingredientNames
    )
}
