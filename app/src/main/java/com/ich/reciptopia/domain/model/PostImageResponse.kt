package com.ich.reciptopia.domain.model

data class PostImageResponse(
    val id :Long? = null,
    val uploadFileName: String? = null,
    val storeFile: String? = null,
    val postId: Long? = null
)