package com.ich.reciptopia.presentation.post_detail

import com.ich.reciptopia.domain.model.Comment
import com.ich.reciptopia.domain.model.Reply

sealed class PostDetailEvent{
    object ClickFavorite: PostDetailEvent()
    object ClickLike: PostDetailEvent()
    object CreateComment: PostDetailEvent()
    data class CommentTextChanged(val text: String): PostDetailEvent()

    data class CommentLikeButtonClick(val comment: Comment, val idx: Int): PostDetailEvent()
    data class ReplyLikeButtonClick(val reply: Reply, val commentIdx: Int, val replyIdx: Int): PostDetailEvent()
}
