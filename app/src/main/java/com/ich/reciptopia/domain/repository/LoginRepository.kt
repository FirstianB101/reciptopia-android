package com.ich.reciptopia.domain.repository

import com.ich.reciptopia.domain.model.Auth
import com.ich.reciptopia.domain.model.User

interface LoginRepository {
    suspend fun loginUser(auth: Auth): User
}