package com.ich.reciptopia.data.remote.dto

import com.ich.reciptopia.domain.model.MainIngredient

data class MainIngredientDto(
    var recipeId: Long? = null,
    var name: String? = null,
    var detail: String? = null
){
    var id: Long? = null
}

fun MainIngredientDto.toMainIngredient(): MainIngredient{
    return MainIngredient(
        recipeId = recipeId,
        name = name,
        detail = detail
    ).also { it.id = id }
}