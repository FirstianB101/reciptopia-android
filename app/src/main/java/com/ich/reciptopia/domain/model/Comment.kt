package com.ich.reciptopia.domain.model

data class Comment(
    val id: Long? = null,
    val ownerId: Long? = null,
    val postId: Long? = null,
    val content: String? = null,
    val createTime: String? = null,
    val owner: Account? = null,
    val replies: List<Reply>? = null,
    val like: Boolean = false
)
