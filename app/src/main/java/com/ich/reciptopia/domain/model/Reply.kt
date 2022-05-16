package com.ich.reciptopia.domain.model

data class Reply(
    val id: Long? = null,
    val ownerId: Long? = null,
    val commentId: Long? = null,
    val content: String? = null
)