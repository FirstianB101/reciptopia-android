package com.ich.reciptopia.data.remote.dto

import com.ich.reciptopia.domain.model.SearchHistory

data class SearchHistoriesDto(
    val searchHistories: Map<String, SearchHistoryDto>
)

fun SearchHistoriesDto.toSearchHistoryList(): List<SearchHistory>{
    val list = mutableListOf<SearchHistory>()
    searchHistories.keys.forEach {
        list.add(searchHistories[it]!!.toSearchHistory())
    }
    return list
}
