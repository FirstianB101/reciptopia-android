package com.ich.reciptopia.data.remote.dto

import com.ich.reciptopia.domain.model.ReplyLikeTag

data class ReplyLikeTagDto(
    var ownerId: Long? = null,
    var replyId: Long? = null
){
    var id: Long? = null
}

fun ReplyLikeTagDto.toReplyLikeTag(): ReplyLikeTag{
    return ReplyLikeTag(
        ownerId = ownerId,
        replyId = replyId
    ).also{ it.id = id }
}