package com.ich.reciptopia.presentation.my_page.sign_up

sealed class SignUpScreenEvent{
    data class EmailChanged(val email: String): SignUpScreenEvent()
    data class PasswordChanged(val password: String): SignUpScreenEvent()
    data class PasswordCheckChanged(val passwordCheck: String): SignUpScreenEvent()
    data class NicknameChanged(val nickname: String): SignUpScreenEvent()
    data class ShowNotification(val show: Boolean): SignUpScreenEvent()
    object SignUp: SignUpScreenEvent()
}
