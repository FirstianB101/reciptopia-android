package com.ich.reciptopia.domain.repository

import com.ich.reciptopia.domain.model.Post
import com.ich.reciptopia.domain.model.RecipePost

interface CommunityRepository {
    suspend fun getPostsByTime(searchQuery: String): List<Post>
    suspend fun getPostsByViews(searchQuery: String): List<Post>
    suspend fun createRecipePost(recipePost: RecipePost): RecipePost
}