package com.ich.reciptopia.presentation.my_page.login

data class LoginState(
    val isLoading: Boolean = false,
    val email: String = "",
    val password: String = "",
)