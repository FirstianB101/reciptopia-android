package com.ich.reciptopia.data.repository

import com.ich.reciptopia.data.remote.ReciptopiaApi
import com.ich.reciptopia.domain.model.Account
import com.ich.reciptopia.domain.model.User
import com.ich.reciptopia.domain.repository.LoginRepository

class LoginRepositoryImpl(
    api: ReciptopiaApi
): LoginRepository {
    override suspend fun loginUser(account: Account): User {
        return User(null, account)
    }
}