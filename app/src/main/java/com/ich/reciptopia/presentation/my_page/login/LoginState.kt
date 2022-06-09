package com.ich.reciptopia.presentation.my_page.login

import com.ich.reciptopia.domain.model.User

data class LoginState(
    val isLoading: Boolean = false,
    val email: String = "",
    val password: String = "",
    val loginUser: User? = null
)