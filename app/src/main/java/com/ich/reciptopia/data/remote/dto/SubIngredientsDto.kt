package com.ich.reciptopia.data.remote.dto

import com.ich.reciptopia.domain.model.SubIngredient

data class SubIngredientsDto(
    val subIngredients: LinkedHashMap<String, SubIngredientDto>
)

fun SubIngredientsDto.toSubIngredientList(): List<SubIngredient>{
    val list = mutableListOf<SubIngredient>()
    subIngredients.keys.forEach {
        list.add(subIngredients[it]!!.toSubIngredient())
    }
    return list
}
