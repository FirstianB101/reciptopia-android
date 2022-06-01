package com.ich.reciptopia.presentation.post_detail.chat

import com.ich.reciptopia.domain.model.*

data class PostDetailChatState(
    val isLoading: Boolean = false,
    val inputText: String = "",
    val curPost: Post? = null,
    val currentUser: User? = null,
    val comments: List<Comment> = emptyList(),
    val commentLikeTags: List<CommentLikeTag> = emptyList(),
    val replyLikeTags: List<ReplyLikeTag> = emptyList(),
    val selectedCommentIdx: Int? = null
)
