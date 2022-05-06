package com.ich.reciptopia.data.remote.dto

import com.ich.reciptopia.domain.model.SubIngredient

data class SubIngredientDto(
    var recipeId: Long? = null,
    var name: String? = null,
    var detail: String? = null
){
    var id: Long? = null
}

fun SubIngredientDto.toSubIngredient(): SubIngredient{
    return SubIngredient(
        recipeId = recipeId,
        name = name,
        detail = detail
    ).also { it.id = id }
}