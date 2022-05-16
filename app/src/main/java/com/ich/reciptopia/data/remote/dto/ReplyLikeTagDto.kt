package com.ich.reciptopia.data.remote.dto

import com.ich.reciptopia.domain.model.ReplyLikeTag

data class ReplyLikeTagDto(
    var id: Long? = null,
    var ownerId: Long? = null,
    var replyId: Long? = null
)

fun ReplyLikeTagDto.toReplyLikeTag(): ReplyLikeTag{
    return ReplyLikeTag(
        id = id,
        ownerId = ownerId,
        replyId = replyId
    )
}