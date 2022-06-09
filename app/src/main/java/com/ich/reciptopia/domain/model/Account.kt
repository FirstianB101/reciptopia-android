package com.ich.reciptopia.domain.model

import android.graphics.Bitmap

data class Account(
    val role: String? = null,
    val id: Long? = null,
    val email: String? = null,
    val nickname: String? = null,
    val profileImage: Bitmap? = null,
    val password: String? = null
)