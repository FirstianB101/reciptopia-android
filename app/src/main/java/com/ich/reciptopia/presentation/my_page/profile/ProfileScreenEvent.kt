package com.ich.reciptopia.presentation.my_page.profile

import android.graphics.Bitmap

sealed class ProfileScreenEvent{
    data class EditProfile(val nickname: String, val profileImg: Bitmap?): ProfileScreenEvent()
    data class EditDialogStateChanged(val show: Boolean): ProfileScreenEvent()
}
