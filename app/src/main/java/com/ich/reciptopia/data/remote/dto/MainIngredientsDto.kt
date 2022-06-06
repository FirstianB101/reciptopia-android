package com.ich.reciptopia.data.remote.dto

import com.ich.reciptopia.domain.model.MainIngredient

data class MainIngredientsDto(
    val mainIngredients: LinkedHashMap<String, List<MainIngredientDto>>
)

fun MainIngredientsDto.toMainIngredientList(): List<MainIngredient>{
    val list = mutableListOf<MainIngredient>()
    mainIngredients.keys.forEach { key ->
        list.addAll(mainIngredients[key]!!.map{it.toMainIngredient()})
    }
    return list
}
