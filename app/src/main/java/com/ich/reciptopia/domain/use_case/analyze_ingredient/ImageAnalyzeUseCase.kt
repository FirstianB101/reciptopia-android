package com.ich.reciptopia.domain.use_case.analyze_ingredient

import android.graphics.Bitmap
import com.ich.reciptopia.common.util.Constants
import com.ich.reciptopia.common.util.Resource
import com.ich.reciptopia.domain.model.ImageResponse
import com.ich.reciptopia.domain.repository.AnalyzeIngredientRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ImageAnalyzeUseCase @Inject constructor(
    private val repository: AnalyzeIngredientRepository
) {
    operator fun invoke(images: List<Bitmap>): Flow<Resource<ImageResponse>> = flow{
        try{
            emit(Resource.Loading<ImageResponse>())

            val result = repository.getAnalyzeResult(images)

            when(result.status){
                "success" -> emit(Resource.Success<ImageResponse>(result))
                "failure" -> emit(Resource.Error<ImageResponse>(result.response_data?.message!!))
            }
        }catch (e: HttpException){
            emit(Resource.Error<ImageResponse>(e.localizedMessage ?: Constants.HTTP_EXCEPTION_COMMENT))
        }catch (e: IOException){
            emit(Resource.Error<ImageResponse>(Constants.CANNOT_CONNECT_SERVER_COMMENT))
        }
    }
}