package com.ich.reciptopia.data.repository

import com.ich.reciptopia.data.remote.ReciptopiaApi
import com.ich.reciptopia.domain.model.Account
import com.ich.reciptopia.domain.model.Auth
import com.ich.reciptopia.domain.model.User
import com.ich.reciptopia.domain.repository.LoginRepository

class LoginRepositoryImpl(
    private val api: ReciptopiaApi
): LoginRepository {
    override suspend fun loginUser(auth: Auth): User {
        val testUser = Account(
            id = 1L,
            email = auth.email,
            nickname = "moNickname",
            profilePictureUrl = null
        )
        return User("token", testUser)
    }
}