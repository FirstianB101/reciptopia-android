package com.ich.reciptopia.domain.model

data class RecipePost(
    val post: Post? = null,
    val mainIngredients: RecipePostMainIngredients? = null,
    val subIngredients: RecipePostSubIngredients? = null,
    val steps: RecipePostSteps? = null
)

data class RecipePostMainIngredients(
    val mainIngredients: List<MainIngredient>? = null
)

data class RecipePostSubIngredients(
    val subIngredients: List<SubIngredient>? = null
)

data class RecipePostSteps(
    val steps: List<Step>? = null
)