package com.ich.reciptopia.data.remote

import com.ich.reciptopia.data.remote.dto.ImageResponseDto
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ImageAnalyzeApi {

    @Multipart
    @POST("multiple_predict")
    suspend fun getAnalyzeResult(
        @Part("version") version: RequestBody = API_VERSION.toRequestBody("text/plain".toMediaTypeOrNull()),
        @Part("appKey") appKey: RequestBody = APP_KEY.toRequestBody("text/plain".toMediaTypeOrNull()),
        @Part files: List<MultipartBody.Part>
    ): ImageResponseDto

    companion object{
        const val APP_KEY = "2022y05m22d"
        const val API_VERSION = "1"
    }
}