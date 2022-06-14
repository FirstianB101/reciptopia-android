package com.ich.reciptopia.domain.use_case.post

import android.graphics.Bitmap
import com.ich.reciptopia.common.util.Constants
import com.ich.reciptopia.common.util.Resource
import com.ich.reciptopia.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetOwnerProfileImageUseCase @Inject constructor(
    private val repository: PostRepository
) {
    operator fun invoke(ownerId: Long): Flow<Resource<Bitmap>> = flow{
        try{
            emit(Resource.Loading<Bitmap>())

            val response = repository.getOwnerProfileImage(ownerId)
            emit(Resource.Success<Bitmap>(response))
        }catch (e: HttpException){
            emit(Resource.Error<Bitmap>(e.localizedMessage ?: Constants.HTTP_EXCEPTION_COMMENT))
        }catch (e: IOException){
            emit(Resource.Error<Bitmap>(Constants.CANNOT_CONNECT_SERVER_COMMENT))
        }
    }
}