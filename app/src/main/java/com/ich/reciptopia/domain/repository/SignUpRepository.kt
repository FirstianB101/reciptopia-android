package com.ich.reciptopia.domain.repository

import com.ich.reciptopia.domain.model.Account
import com.ich.reciptopia.domain.model.Exist

interface SignUpRepository {
    suspend fun accountExists(email: String): Exist
    suspend fun createAccount(account: Account): Account
}