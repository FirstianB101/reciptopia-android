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

    override suspend fun getComments(postId: Long): List<Comment> {
        return api.getCommentsByPostId(postId).toCommentList()
    }

    override suspend fun getReplies(commentId: Long): List<Reply> {
        return api.getRepliesByCommentId(commentId).toReplyList()
    }

    override suspend fun createComment(comment: Comment): Comment {
        return api.createComment(comment).toComment()
    }

    override suspend fun getCommentLikeTags(ownerId: Long?): List<CommentLikeTag> {
        return api.getCommentLikeTags().map{it.toCommentLikeTag()}
    }

    override suspend fun getReplyLikeTags(ownerId: Long?): List<ReplyLikeTag> {
        return api.getReplyLikeTags().map{it.toReplyLikeTag()}
    }

    override suspend fun likeComment(ownerId: Long?, commentId: Long): CommentLikeTag {
        return api.createCommentLikeTag(CommentLikeTag(ownerId = ownerId, commentId = commentId)).toCommentLikeTag()
    }

    override suspend fun likeReply(ownerId: Long?, replyId: Long): ReplyLikeTag {
        return api.createReplyLikeTag(ReplyLikeTag(ownerId = ownerId, replyId = replyId)).toReplyLikeTag()
    }

    override suspend fun unlikeComment(commentLikeTagId: Long?) {
        api.deleteCommentLikeTag(commentLikeTagId)
    }

    override suspend fun unlikeReply(replyLikeTagId: Long?) {
        api.deleteReplyLikeTag(replyLikeTagId)
    }
}