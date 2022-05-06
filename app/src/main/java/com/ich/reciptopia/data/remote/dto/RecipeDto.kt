package com.ich.reciptopia.data.remote.dto

import com.ich.reciptopia.domain.model.Recipe

data class RecipeDto(
    var postId: Long? = null
){
    var id: Long? = null
}

fun RecipeDto.toRecipe(): Recipe{
    return Recipe(
        postId = postId
    ).also { it.id = id }
}