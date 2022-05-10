package com.ich.reciptopia.presentation.my_page.profile

sealed class ProfileScreenEvent{
    data class ChangeNickname(val nickname: String): ProfileScreenEvent()
    data class EditDialogStateChanged(val show: Boolean): ProfileScreenEvent()
}
