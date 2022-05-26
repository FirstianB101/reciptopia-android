package com.ich.reciptopia.data.repository

import android.graphics.Bitmap
import android.os.Build
import androidx.annotation.RequiresApi
import com.ich.reciptopia.common.util.DateTimeUtils
import com.ich.reciptopia.data.remote.ImageAnalyzeApi
import com.ich.reciptopia.data.remote.dto.toImageResponseBody
import com.ich.reciptopia.domain.model.ImageResponse
import com.ich.reciptopia.domain.repository.AnalyzeIngredientRepository
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream

class AnalyzeIngredientRepositoryImpl(
    private val api: ImageAnalyzeApi
) : AnalyzeIngredientRepository {

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getAnalyzeResult(images: List<Bitmap>): ImageResponse {
        val files = images.mapIndexed { idx, image ->

            val stream = ByteArrayOutputStream()
            image.compress(Bitmap.CompressFormat.JPEG, 50, stream)
            val dateTime = DateTimeUtils.getCurrentDatetimeString()
            val fileName = "${dateTime}+${idx}(android).jpeg"
            val bitmapRequestBody = RequestBody.create("image/jpeg".toMediaType(), stream.toByteArray())

            val bitmapMultipartBody: MultipartBody.Part =
                MultipartBody.Part.createFormData("files", fileName, bitmapRequestBody)
            bitmapMultipartBody
        }

        return api.getAnalyzeResult(files = files).toImageResponseBody()
    }
}