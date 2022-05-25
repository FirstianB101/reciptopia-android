package com.ich.reciptopia.data.remote.dto

import com.ich.reciptopia.domain.model.Comment

data class CommentsDto(
    val comments: Map<String, CommentDto>
)

fun CommentsDto.toCommentList(): List<Comment>{
    val list = mutableListOf<Comment>()
    comments.keys.forEach {
        list.add(comments[it]!!.toComment())
    }
    return list
}
