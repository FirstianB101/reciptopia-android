package com.ich.reciptopia.domain.model

data class SearchHistory(
    var ownerId: Long? = null,
    var ingredientNames: List<String?> = emptyList()
){
    var id: Long? = null
}
