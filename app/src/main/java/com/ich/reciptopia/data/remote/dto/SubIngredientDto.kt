package com.ich.reciptopia.data.remote.dto

import com.ich.reciptopia.domain.model.SubIngredient

data class SubIngredientDto(
    var id: Long? = null,
    var recipeId: Long? = null,
    var name: String? = null,
    var detail: String? = null
)

fun SubIngredientDto.toSubIngredient(): SubIngredient{
    return SubIngredient(
        id = id,
        recipeId = recipeId,
        name = name,
        detail = detail
    )
}