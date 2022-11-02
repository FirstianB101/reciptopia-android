package com.ich.reciptopia.data.repository

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import androidx.annotation.RequiresApi
import com.ich.reciptopia.common.util.DateTimeUtils
import com.ich.reciptopia.data.remote.ReciptopiaApi
import com.ich.reciptopia.data.remote.dto.toAccount
import com.ich.reciptopia.data.remote.dto.toProfileImageResponse
import com.ich.reciptopia.domain.model.Account
import com.ich.reciptopia.domain.model.ProfileImageResponse
import com.ich.reciptopia.domain.repository.ProfileRepository
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream

class ProfileRepositoryImpl(
    private val api: ReciptopiaApi
): ProfileRepository {

    override suspend fun patchAccount(accountId: Long, account: Account): Account {
        return api.patchAccount(accountId, account).toAccount()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun uploadProfileImg(ownerId: Long, img: Bitmap): ProfileImageResponse{
        val stream = ByteArrayOutputStream()
        img.compress(Bitmap.CompressFormat.JPEG, 30, stream)
        val dateTime = DateTimeUtils.getCurrentDatetimeString()
        val fileName = "${dateTime}(android).jpeg"
        val bitmapRequestBody = RequestBody.create("image/jpeg".toMediaType(), stream.toByteArray())

        val bitmapMultipartBody: MultipartBody.Part =
            MultipartBody.Part.createFormData("imgFile", fileName, bitmapRequestBody)

        return api.putAccountProfileImage(ownerId, bitmapMultipartBody).toProfileImageResponse()
    }

    override suspend fun getAccountProfileImage(ownerId: Long): Bitmap? {
        val response = api.getAccountProfileImage(ownerId)
        return BitmapFactory.decodeStream(response.byteStream())
    }
}