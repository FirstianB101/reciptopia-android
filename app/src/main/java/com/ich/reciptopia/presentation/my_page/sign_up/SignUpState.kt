package com.ich.reciptopia.presentation.my_page.sign_up

data class SignUpState(
    val isLoading: Boolean = false,
    val email: String = "",
    val password: String = "",
    val passwordCheck: String = "",
    val nickname: String = "",
    val showNotification: Boolean = false,
    val emailExist: Boolean = false
)
