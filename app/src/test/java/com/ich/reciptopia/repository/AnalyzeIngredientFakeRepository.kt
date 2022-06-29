package com.ich.reciptopia.repository

import android.graphics.Bitmap
import com.ich.reciptopia.domain.model.AnalyzeResult
import com.ich.reciptopia.domain.model.ImageResponse
import com.ich.reciptopia.domain.repository.AnalyzeIngredientRepository
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class AnalyzeIngredientFakeRepository: AnalyzeIngredientRepository {
    val successResponse = ImageResponse(
        response_data = AnalyzeResult(
            predicts = mapOf(
                "1" to "ingredient1",
                "2" to "ingredient2",
                "3" to "ingredient3"
            )
        ),
        status = "success"
    )

    val errorResponse = ImageResponse(
        response_data = AnalyzeResult(
            message = "error response"
        ),
        status = "failure"
    )

    var exception: ExceptionCase? = null

    override suspend fun getAnalyzeResult(images: List<Bitmap>): ImageResponse {
        if(exception == ExceptionCase.HttpException){
            throw HttpException(Response.error<ResponseBody>(500 ,ResponseBody.create("plain/text".toMediaTypeOrNull(),"http")))
        }else if(exception == ExceptionCase.IOException){
            throw IOException("io")
        }
        return successResponse
    }

    enum class ExceptionCase{
        HttpException,
        IOException
    }
}