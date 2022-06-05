package com.ich.reciptopia.data.remote.dto

import com.ich.reciptopia.domain.model.ProfileImageResponse

data class ProfileImageResponseDto(
    val id :Long? = null,
    val uploadFileName: String? = null,
    val storeFile: String? = null,
    val ownerId: Long? = null
)

fun ProfileImageResponseDto.toProfileImageResponse(): ProfileImageResponse{
    return ProfileImageResponse(
        id = id,
        uploadFileName = uploadFileName,
        storeFile = storeFile,
        ownerId = ownerId
    )
}