package com.ich.reciptopia.data.remote.dto

import com.ich.reciptopia.domain.model.SearchHistory

data class SearchHistoryDto(
    var ownerId: Long? = null,
    var ingredientNames: List<String?> = emptyList()
){
    var id: Long? = null
}

fun SearchHistoryDto.toSearchHistory(): SearchHistory{
    return SearchHistory(
        ownerId = ownerId,
        ingredientNames = ingredientNames
    ).also { it.id = id }
}
