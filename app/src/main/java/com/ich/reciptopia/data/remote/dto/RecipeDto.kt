package com.ich.reciptopia.data.remote.dto

import com.ich.reciptopia.domain.model.Recipe

data class RecipeDto(
    var id: Long? = null,
    var postId: Long? = null
)

fun RecipeDto.toRecipe(): Recipe{
    return Recipe(
        id = id,
        postId = postId
    )
}