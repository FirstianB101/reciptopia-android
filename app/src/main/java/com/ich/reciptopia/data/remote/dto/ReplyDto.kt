package com.ich.reciptopia.data.remote.dto

import com.ich.reciptopia.domain.model.Reply

data class ReplyDto(
    var id: Long? = null,
    var ownerId: Long? = null,
    var commentId: Long? = null,
    var content: String? = null
)

fun ReplyDto.toReply(): Reply{
    return Reply(
        id = id,
        ownerId = ownerId,
        commentId = commentId,
        content = content
    )
}