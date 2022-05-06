package com.ich.reciptopia.data.remote.dto

import com.ich.reciptopia.domain.model.Comment

data class CommentDto(
    var ownerId: Long? = null,
    var postId: Long? = null,
    var content: String? = null
){
    var id: Long? = null
}

fun CommentDto.toComment(): Comment {
    return Comment(
        ownerId = ownerId,
        postId = postId,
        content = content
    ).also{ it.id = id }
}