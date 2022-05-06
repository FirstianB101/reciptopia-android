package com.ich.reciptopia.data.remote.dto

import com.ich.reciptopia.domain.model.CommentLikeTag

data class CommentLikeTagDto(
    var ownerId: Long? = null,
    var commentId: Long? = null
){
    var id: Long? = null
}

fun CommentLikeTagDto.toCommentLikeTag(): CommentLikeTag{
    return CommentLikeTag(
        ownerId = ownerId,
        commentId = commentId
    ).also { it.id = id }
}