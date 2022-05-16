package com.ich.reciptopia.data.remote.dto

import com.ich.reciptopia.domain.model.MainIngredient

data class MainIngredientDto(
    var id: Long? = null,
    var recipeId: Long? = null,
    var name: String? = null,
    var detail: String? = null
)

fun MainIngredientDto.toMainIngredient(): MainIngredient{
    return MainIngredient(
        id = id,
        recipeId = recipeId,
        name = name,
        detail = detail
    )
}