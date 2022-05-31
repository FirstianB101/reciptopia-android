package com.ich.reciptopia.data.remote.dto

data class RecipePostDto(
    val post: PostDto? = null,
    val recipe: RecipeDto? = null,
    val mainIngredients: Map<String, MainIngredientDto>? = null,
    val subIngredients: Map<String, SubIngredientDto>? = null,
    val steps: Map<String, StepDto>? = null
)

