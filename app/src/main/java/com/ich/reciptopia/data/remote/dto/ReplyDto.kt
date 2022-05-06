package com.ich.reciptopia.data.remote.dto

import com.ich.reciptopia.domain.model.Reply

data class ReplyDto(
    var ownerId: Long? = null,
    var commentId: Long? = null,
    var content: String? = null
){
    var id: Long? = null
}

fun ReplyDto.toReply(): Reply{
    return Reply(
        ownerId = ownerId,
        commentId = commentId,
        content = content
    ).also { it.id = id }
}