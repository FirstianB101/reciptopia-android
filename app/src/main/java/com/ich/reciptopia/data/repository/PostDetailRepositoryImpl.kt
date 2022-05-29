package com.ich.reciptopia.data.repository

import com.ich.reciptopia.data.remote.ReciptopiaApi
import com.ich.reciptopia.domain.model.*
import com.ich.reciptopia.domain.repository.PostDetailRepository

class PostDetailRepositoryImpl(
    private val api: ReciptopiaApi
): PostDetailRepository {

    override suspend fun getPost(postId: Long): Post {
        return RepositoryTestUtils.testPosts.find { it.id == postId }!!
    }

    override suspend fun getRecipe(postId: Long): Recipe {
        return RepositoryTestUtils.testRecipes.find { it.postId == postId }!!
    }

    override suspend fun getSteps(recipeId: Long): List<Step> {
        return RepositoryTestUtils.testSteps.filter { it.recipeId == recipeId }
    }

    override suspend fun getMainIngredients(postId: Long): List<MainIngredient> {
        // 테스트에선 recipeid == postid이므로 임시로 postid를 recipeid로 사용함
        return RepositoryTestUtils.testMainIngredients.filter { it.recipeId == postId }
    }

    override suspend fun getSubIngredients(postId: Long): List<SubIngredient> {
        // 테스트에선 recipeid == postid이므로 임시로 postid를 recipeid로 사용함
        return RepositoryTestUtils.testSubIngredients.filter { it.recipeId == postId }
    }

    override suspend fun getComments(postId: Long): List<Comment> {
        return RepositoryTestUtils.testComments.filter { it.postId == postId }
    }

    override suspend fun getReplies(commentId: Long): List<Reply> {
        return RepositoryTestUtils.testReplies.filter { it.commentId == commentId }
    }
}