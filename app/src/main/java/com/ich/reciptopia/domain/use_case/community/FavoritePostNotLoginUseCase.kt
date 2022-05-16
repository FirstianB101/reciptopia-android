package com.ich.reciptopia.domain.use_case.community

import com.ich.reciptopia.common.util.Constants
import com.ich.reciptopia.common.util.Resource
import com.ich.reciptopia.domain.model.Post
import com.ich.reciptopia.domain.repository.CommunityRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class FavoritePostNotLoginUseCase  @Inject constructor(
    private val repository: CommunityRepository
){
    operator fun invoke(post: Post): Flow<Resource<Unit>> = flow {
        try{
            emit(Resource.Loading<Unit>())

            repository.favoritePost(post)
            emit(Resource.Success<Unit>(null))

        }catch (e: HttpException){
            emit(Resource.Error<Unit>(e.localizedMessage ?: Constants.HTTP_EXCEPTION_COMMENT))
        }catch (e: IOException){
            emit(Resource.Error<Unit>(Constants.CANNOT_CONNECT_SERVER_COMMENT))
        }
    }
}