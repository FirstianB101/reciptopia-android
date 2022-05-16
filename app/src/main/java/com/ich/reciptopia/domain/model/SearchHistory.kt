package com.ich.reciptopia.domain.model

data class SearchHistory(
    val id: Long? = null,
    val ownerId: Long? = null,
    val ingredientNames: List<String?> = emptyList()
)
