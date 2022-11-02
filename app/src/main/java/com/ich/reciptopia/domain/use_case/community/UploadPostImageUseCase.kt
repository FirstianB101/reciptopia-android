package com.ich.reciptopia.domain.use_case.community

import android.net.Uri
import com.ich.reciptopia.common.util.Constants
import com.ich.reciptopia.common.util.Resource
import com.ich.reciptopia.domain.model.Post
import com.ich.reciptopia.domain.model.RecipePost
import com.ich.reciptopia.domain.repository.CommunityRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class UploadPostImageUseCase @Inject constructor(
    private val repository: CommunityRepository
) {
    operator fun invoke(uri: Uri): Flow<Resource<String>> = flow{
        try{
            emit(Resource.Loading<String>())

            val uri = repository.uploadPostImage(uri)
            emit(Resource.Success<String>(uri))
        }catch(e: CancellationException){
            emit(Resource.Error<String>(e.localizedMessage))
        }catch (e: HttpException){
            emit(Resource.Error<String>(e.localizedMessage ?: Constants.HTTP_EXCEPTION_COMMENT))
        }catch (e: IOException){
            emit(Resource.Error<String>(Constants.CANNOT_CONNECT_SERVER_COMMENT))
        }
    }
}