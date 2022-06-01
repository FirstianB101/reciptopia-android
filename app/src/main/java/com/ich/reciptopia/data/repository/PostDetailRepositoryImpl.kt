package com.ich.reciptopia.data.repository

import com.ich.reciptopia.data.remote.ReciptopiaApi
import com.ich.reciptopia.data.remote.dto.*
import com.ich.reciptopia.domain.model.*
import com.ich.reciptopia.domain.repository.PostDetailRepository

class PostDetailRepositoryImpl(
    private val api: ReciptopiaApi
): PostDetailRepository {

    override suspend fun getPost(postId: Long): Post {
        return api.getPostsByIds(listOf(postId)).toPostList().first()
    }

    override suspend fun getRecipe(postId: Long): Recipe {
        return api.getRecipeByPostId(listOf(postId)).toRecipeList().first()
    }

    override suspend fun getSteps(recipeId: Long): List<Step> {
        return api.getStepsByRecipeId(recipeId).toStepList()
    }

    override suspend fun getMainIngredients(postId: Long): List<MainIngredient> {
        return api.getMainIngredientsByPostId(postId).toMainIngredientList()
    }

    override suspend fun getSubIngredients(postId: Long): List<SubIngredient> {
        return api.getSubIngredientsByPostId(postId).toSubIngredientList()
    }
}