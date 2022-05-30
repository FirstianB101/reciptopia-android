package com.ich.reciptopia.domain.repository

import com.ich.reciptopia.domain.model.*

interface PostDetailRepository {
    suspend fun getPost(postId: Long): Post
    suspend fun getRecipe(postId: Long): Recipe
    suspend fun getSteps(recipeId: Long): List<Step>
    suspend fun getMainIngredients(postId: Long): List<MainIngredient>
    suspend fun getSubIngredients(postId: Long): List<SubIngredient>
    suspend fun getComments(postId: Long): List<Comment>
    suspend fun getReplies(commentId: Long): List<Reply>
    suspend fun getCommentLikeTags(ownerId: Long?): List<CommentLikeTag>
    suspend fun getReplyLikeTags(ownerId: Long?): List<ReplyLikeTag>

    suspend fun createComment(comment: Comment): Comment
    suspend fun likeComment(ownerId: Long?, commentId: Long): CommentLikeTag
    suspend fun likeReply(ownerId: Long?, replyId: Long): ReplyLikeTag
    suspend fun unlikeComment(commentLikeTagId : Long?)
    suspend fun unlikeReply(replyLikeTagId: Long?)
}