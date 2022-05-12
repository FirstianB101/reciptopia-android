package com.ich.reciptopia.presentation.post_detail

sealed class PostDetailEvent{
    data class ClickFavorite(val postId: Long): PostDetailEvent()
    data class ClickLike(val postId: Long): PostDetailEvent()
    data class CommentTextChanged(val text: String): PostDetailEvent()
    data class CreateComment(val postId: Long): PostDetailEvent()
}
