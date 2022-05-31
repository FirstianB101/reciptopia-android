package com.ich.reciptopia.data.repository

import com.ich.reciptopia.data.remote.ReciptopiaApi
import com.ich.reciptopia.data.remote.dto.toPostList
import com.ich.reciptopia.domain.model.Post
import com.ich.reciptopia.domain.model.RecipePost
import com.ich.reciptopia.domain.repository.CommunityRepository

class CommunityRepositoryImpl(
    private val api: ReciptopiaApi
): CommunityRepository {

    override suspend fun getPostsByTime(searchQuery: String): List<Post> {
        return api.getPostsByTitleLike(searchQuery, "id,desc").toPostList()
    }

    override suspend fun getPostsByViews(searchQuery: String): List<Post> {
        return api.getPostsByTitleLike(searchQuery, "views,desc").toPostList()
    }

    override suspend fun createRecipePost(recipePost: RecipePost): RecipePost {
        api.createRecipePost(recipePost)
        return RecipePost()
    }
}