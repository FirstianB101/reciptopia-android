package com.ich.reciptopia.data.remote

import android.graphics.Bitmap
import okhttp3.ResponseBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Query

interface ImageAnalyzeApi {

    @Multipart
    @POST("http://ubinetlab.asuscomm.com:50001/multiple_predict")
    suspend fun getAnalyzeResult(
        @Query("version") version: String = API_VERSION,
        @Query("appKey") appKey: String = APP_KEY,
        @Query("files") images: List<Bitmap>
    ): ResponseBody

    companion object{
        const val APP_KEY = "2022y05m22d"
        const val API_VERSION = "1"
    }
}