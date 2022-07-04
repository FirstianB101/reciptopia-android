package com.ich.reciptopia.presentation.my_page.login

import com.ich.reciptopia.domain.model.Account
import com.ich.reciptopia.domain.model.Auth
import com.ich.reciptopia.domain.model.User
import com.ich.reciptopia.domain.repository.LoginRepository

class LoginFakeRepository: LoginRepository {
    override suspend fun loginUser(auth: Auth): User {
        return User(
            token = "testtoken",
            account = Account(
                id = 1,
                email = auth.email,
                nickname = "testnickname",
                role = "USER"
            )
        )
    }
}