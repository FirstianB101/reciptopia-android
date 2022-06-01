package com.ich.reciptopia.presentation.post_detail

sealed class PostDetailEvent{
    object ClickFavorite: PostDetailEvent()
    object ClickLike: PostDetailEvent()
}
