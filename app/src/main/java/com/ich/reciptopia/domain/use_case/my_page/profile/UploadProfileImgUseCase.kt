package com.ich.reciptopia.domain.use_case.my_page.profile

import android.graphics.Bitmap
import com.ich.reciptopia.common.util.Constants
import com.ich.reciptopia.common.util.Resource
import com.ich.reciptopia.domain.model.ProfileImageResponse
import com.ich.reciptopia.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class UploadProfileImgUseCase @Inject constructor(
    private val repository: ProfileRepository
) {
    operator fun invoke(ownerId: Long, image: Bitmap): Flow<Resource<ProfileImageResponse>> = flow{
        try{
            emit(Resource.Loading<ProfileImageResponse>())

            val response = repository.uploadProfileImg(ownerId, image)
            emit(Resource.Success<ProfileImageResponse>(response))
        }catch (e: HttpException){
            emit(Resource.Error<ProfileImageResponse>(e.localizedMessage ?: Constants.HTTP_EXCEPTION_COMMENT))
        }catch (e: IOException){
            emit(Resource.Error<ProfileImageResponse>(Constants.CANNOT_CONNECT_SERVER_COMMENT))
        }
    }
}