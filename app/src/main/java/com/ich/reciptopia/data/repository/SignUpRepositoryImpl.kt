package com.ich.reciptopia.data.repository

import com.ich.reciptopia.data.remote.ReciptopiaApi
import com.ich.reciptopia.domain.model.Account
import com.ich.reciptopia.domain.model.Exist
import com.ich.reciptopia.domain.repository.SignUpRepository

class SignUpRepositoryImpl(
    api: ReciptopiaApi
): SignUpRepository {
    override suspend fun accountExists(email: String): Exist {
        return Exist(
            exist = email.isBlank() || email == "exist"
        )
    }

    override suspend fun createAccount(account: Account): Account {
        return account
    }
}