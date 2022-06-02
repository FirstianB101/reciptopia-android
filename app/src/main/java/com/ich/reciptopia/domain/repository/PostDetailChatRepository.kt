package com.ich.reciptopia.domain.repository

import com.ich.reciptopia.domain.model.*

interface PostDetailChatRepository {
    suspend fun getOwner(accountId: Long): Account
    suspend fun getComments(postId: Long): List<Comment>
    suspend fun getReplies(commentId: Long): List<Reply>
    suspend fun getCommentLikeTags(ownerId: Long?): List<CommentLikeTag>
    suspend fun getReplyLikeTags(ownerId: Long?): List<ReplyLikeTag>

    suspend fun createComment(comment: Comment): Comment
    suspend fun createReply(reply: Reply): Reply
    suspend fun likeComment(ownerId: Long?, commentId: Long): CommentLikeTag
    suspend fun likeReply(ownerId: Long?, replyId: Long): ReplyLikeTag
    suspend fun unlikeComment(commentLikeTagId : Long?)
    suspend fun unlikeReply(replyLikeTagId: Long?)
    suspend fun deleteComment(commentId: Long)
    suspend fun deleteReply(replyId: Long)
}