package com.ich.reciptopia.presentation.my_page.sign_up

import com.ich.reciptopia.domain.model.Account
import com.ich.reciptopia.domain.model.Exist
import com.ich.reciptopia.domain.repository.SignUpRepository

class SignUpFakeRepository: SignUpRepository {
    val users = mutableListOf<Account>()

    override suspend fun accountExists(email: String): Exist {
        val user = users.find { it.email == email }
        return Exist(user != null)
    }

    override suspend fun createAccount(account: Account): Account {
        users.add(account)
        return account
    }
}