package com.ich.reciptopia.domain.repository

import android.graphics.Bitmap
import com.ich.reciptopia.domain.model.*

interface PostDetailRepository {
    suspend fun getPost(postId: Long): Post
    suspend fun getRecipe(postId: Long): Recipe
    suspend fun getSteps(recipeId: Long): List<Step>
    suspend fun getMainIngredients(postId: Long): List<MainIngredient>
    suspend fun getSubIngredients(recipeId: Long): List<SubIngredient>
    suspend fun deletePost(postId: Long)
}