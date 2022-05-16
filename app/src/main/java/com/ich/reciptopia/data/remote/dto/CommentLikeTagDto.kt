package com.ich.reciptopia.data.remote.dto

import com.ich.reciptopia.domain.model.CommentLikeTag

data class CommentLikeTagDto(
    var id: Long? = null,
    var ownerId: Long? = null,
    var commentId: Long? = null
)

fun CommentLikeTagDto.toCommentLikeTag(): CommentLikeTag{
    return CommentLikeTag(
        id = id,
        ownerId = ownerId,
        commentId = commentId
    )
}