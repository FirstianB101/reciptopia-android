package com.ich.reciptopia.data.remote.dto

import com.ich.reciptopia.domain.model.Reply

data class RepliesDto(
    val replies: LinkedHashMap<String, ReplyDto>
)

fun RepliesDto.toReplyList(): List<Reply>{
    val list = mutableListOf<Reply>()
    replies.keys.forEach {
        list.add(replies[it]!!.toReply())
    }
    return list
}