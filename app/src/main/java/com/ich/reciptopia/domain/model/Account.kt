package com.ich.reciptopia.domain.model

data class Account(
    val role: String? = null,
    val id: Long? = null,
    val email: String? = null,
    val nickname: String? = null,
    val profilePictureUrl: String? = null,
    val password: String? = null
)