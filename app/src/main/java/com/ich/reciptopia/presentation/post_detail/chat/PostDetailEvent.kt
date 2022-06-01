package com.ich.reciptopia.presentation.post_detail.chat

import com.ich.reciptopia.domain.model.Comment
import com.ich.reciptopia.domain.model.Reply

sealed class PostDetailChatEvent{
    object CreateCommentOrReply: PostDetailChatEvent()
    data class CommentTextChanged(val text: String): PostDetailChatEvent()

    data class CommentLikeButtonClick(val comment: Comment, val idx: Int): PostDetailChatEvent()
    data class SelectComment(val idx: Int): PostDetailChatEvent()
    data class ReplyLikeButtonClick(val reply: Reply, val commentIdx: Int, val replyIdx: Int): PostDetailChatEvent()
}
