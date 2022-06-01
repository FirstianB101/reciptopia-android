package com.ich.reciptopia.domain.use_case.post_detail.chat

data class PostDetailChatUseCases(
    val getComments: GetCommentsUseCase,
    val getReplies: GetRepliesUseCase,
    val createComment: CreateCommentUseCase,
    val createReply: CreateReplyUseCase,
    val getCommentLikeTags: GetCommentLikeTagsUseCase,
    val getReplyLikeTags: GetReplyLikeTagsUseCase,
    val likeComment: LikeCommentUseCase,
    val unlikeComment: UnLikeCommentUseCase,
    val likeReply: LikeReplyUseCase,
    val unlikeReply: UnLikeReplyUseCase,
    val getOwner: GetCommentOwnerUseCase
)