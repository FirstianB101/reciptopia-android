package com.ich.reciptopia.presentation.my_page.profile

data class ProfileState(
    val isLoading: Boolean = false,
    val nickname: String = "",
    val showEditDialogState: Boolean = false,
    val email: String = ""
)
