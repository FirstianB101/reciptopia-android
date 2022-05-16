package com.ich.reciptopia.domain.model

data class Step(
    val id: Long? = null,
    val recipeId: Long,
    val description: String? = null,
    val pictureUrl: String? = null
)
