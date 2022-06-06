package com.ich.reciptopia.data.remote.dto

import com.ich.reciptopia.domain.model.Recipe

data class RecipesDto(
    val recipes: LinkedHashMap<String, RecipeDto>?
)

fun RecipesDto.toRecipeList(): List<Recipe>{
    val list = mutableListOf<Recipe>()
    recipes?.keys?.forEach {
        list.add(recipes[it]!!.toRecipe())
    }
    return list
}
