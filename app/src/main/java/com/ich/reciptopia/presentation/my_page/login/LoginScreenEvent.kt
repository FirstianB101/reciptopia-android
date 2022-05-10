package com.ich.reciptopia.presentation.my_page.login

sealed class LoginScreenEvent{
    data class EmailChanged(val email: String): LoginScreenEvent()
    data class PasswordChanged(val password: String): LoginScreenEvent()
    object Login: LoginScreenEvent()
    object Logout: LoginScreenEvent()
}
