package com.ich.reciptopia.data.repository

import com.ich.reciptopia.data.remote.ReciptopiaApi
import com.ich.reciptopia.data.repository.RepositoryTestUtils.testCommentLikeTags
import com.ich.reciptopia.data.repository.RepositoryTestUtils.testReplyLikeTags
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

    override suspend fun createComment(comment: Comment): Comment {
        RepositoryTestUtils.testComments.add(
            comment.copy(id = RepositoryTestUtils.nextCommentId++)
        )
        return comment
    }

    override suspend fun getCommentLikeTags(ownerId: Long?): List<CommentLikeTag> {
        return testCommentLikeTags.filter { it.ownerId == ownerId }
    }

    override suspend fun getReplyLikeTags(ownerId: Long?): List<ReplyLikeTag> {
        return testReplyLikeTags.filter { it.ownerId == ownerId }
    }

    override suspend fun likeComment(ownerId: Long?, commentId: Long): CommentLikeTag {
        return CommentLikeTag(
            id = RepositoryTestUtils.nextCommentLikeTagId++,
            ownerId = ownerId,
            commentId = commentId
        )
    }

    override suspend fun likeReply(ownerId: Long?, replyId: Long): ReplyLikeTag {
        return ReplyLikeTag(
            id = RepositoryTestUtils.nextReplyLikeTagId++,
            ownerId = ownerId,
            replyId = replyId
        )
    }

    override suspend fun unlikeComment(commentLikeTagId: Long?) {
        testCommentLikeTags.forEachIndexed { idx, commentLikeTag ->
            if(commentLikeTag.id == commentLikeTagId){
                testCommentLikeTags.removeAt(idx)
                return
            }
        }
    }

    override suspend fun unlikeReply(replyLikeTagId: Long?) {
        testReplyLikeTags.forEachIndexed { idx, replyLikeTag ->
            if(replyLikeTag.id == replyLikeTagId){
                testReplyLikeTags.removeAt(idx)
                return
            }
        }
    }
}