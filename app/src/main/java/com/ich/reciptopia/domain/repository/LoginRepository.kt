package com.ich.reciptopia.domain.repository

import com.ich.reciptopia.domain.model.Account
import com.ich.reciptopia.domain.model.User

interface LoginRepository {
    suspend fun loginUser(account: Account): User
}