package com.ich.reciptopia.data.remote.dto

import com.ich.reciptopia.domain.model.MainIngredient

data class MainIngredientsDto(
    val mainIngredients: Map<String, MainIngredientDto>
)

fun MainIngredientsDto.toMainIngredientList(): List<MainIngredient>{
    val list = mutableListOf<MainIngredient>()
    mainIngredients.keys.forEach {
        list.add(mainIngredients[it]!!.toMainIngredient())
    }
    return list
}
