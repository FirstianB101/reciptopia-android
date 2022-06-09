package com.ich.reciptopia.domain.repository

import android.graphics.Bitmap
import com.ich.reciptopia.domain.model.Account
import com.ich.reciptopia.domain.model.ProfileImageResponse

interface ProfileRepository {
    suspend fun patchAccount(accountId: Long, account: Account): Account
    suspend fun uploadProfileImg(ownerId: Long, img: Bitmap): ProfileImageResponse
    suspend fun getAccountProfileImage(ownerId: Long): Bitmap?
}