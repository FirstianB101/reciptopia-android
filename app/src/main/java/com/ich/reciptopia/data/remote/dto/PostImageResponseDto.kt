package com.ich.reciptopia.data.remote.dto

import com.ich.reciptopia.domain.model.PostImageResponse

data class PostImageResponseDto(
    val id :Long? = null,
    val uploadFileName: String? = null,
    val storeFile: String? = null,
    val postId: Long? = null
)

fun PostImageResponseDto.toPostImageResponse(): PostImageResponse {
    return PostImageResponse(
        id = id,
        uploadFileName = uploadFileName,
        storeFile = storeFile,
        postId = postId
    )
}