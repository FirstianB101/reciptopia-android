package com.ich.reciptopia.presentation.my_page.profile

import android.graphics.Bitmap
import com.ich.reciptopia.domain.model.Account

data class ProfileState(
    val isLoading: Boolean = false,
    val showEditDialogState: Boolean = false,
    val curAccount: Account? = null,
    val profileImage: Bitmap? = null
)
