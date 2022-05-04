package com.ich.reciptopia.presentation.my_page.sign_up

data class SignUpState(
    val email: String = "",
    val password: String = "",
    val passwordCheck: String = "",
    val nickname: String = "",
    val notificationTitle: String = "",
    val notificationContent: String = "",
    val showNotification: Boolean = false
)
