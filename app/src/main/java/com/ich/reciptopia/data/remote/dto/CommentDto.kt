package com.ich.reciptopia.data.remote.dto

import com.ich.reciptopia.domain.model.Comment

data class CommentDto(
    var id: Long? = null,
    var ownerId: Long? = null,
    var postId: Long? = null,
    var content: String? = null
)

fun CommentDto.toComment(): Comment {
    return Comment(
        id = id,
        ownerId = ownerId,
        postId = postId,
        content = content
    )
}