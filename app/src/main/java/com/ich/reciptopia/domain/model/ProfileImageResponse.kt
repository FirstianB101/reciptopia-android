package com.ich.reciptopia.domain.model

data class ProfileImageResponse(
    val id :Long? = null,
    val uploadFileName: String? = null,
    val storeFile: String? = null,
    val ownerId: Long? = null
)