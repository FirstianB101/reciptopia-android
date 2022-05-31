package com.ich.reciptopia.data.repository

import com.ich.reciptopia.data.remote.ReciptopiaApi
import com.ich.reciptopia.data.remote.dto.toUser
import com.ich.reciptopia.domain.model.Auth
import com.ich.reciptopia.domain.model.User
import com.ich.reciptopia.domain.repository.LoginRepository

class LoginRepositoryImpl(
    private val api: ReciptopiaApi
): LoginRepository {
    override suspend fun loginUser(auth: Auth): User {
        return api.getToken(auth).toUser()
    }
}