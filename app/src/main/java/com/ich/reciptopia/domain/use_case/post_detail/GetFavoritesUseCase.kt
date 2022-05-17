package com.ich.reciptopia.domain.use_case.post_detail

import com.ich.reciptopia.common.util.Constants
import com.ich.reciptopia.common.util.Resource
import com.ich.reciptopia.domain.model.Favorite
import com.ich.reciptopia.domain.repository.PostDetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetFavoritesUseCase @Inject constructor(
    private val repository: PostDetailRepository
) {
    suspend operator fun invoke(userId: Long): Flow<Resource<List<Favorite>>> = flow{
        try{
            emit(Resource.Loading<List<Favorite>>())

            val favorites = repository.getFavorites(userId)
            emit(Resource.Success<List<Favorite>>(favorites))

        }catch (e: HttpException){
            emit(Resource.Error<List<Favorite>>(e.localizedMessage ?: Constants.HTTP_EXCEPTION_COMMENT))
        }catch (e: IOException){
            emit(Resource.Error<List<Favorite>>(Constants.CANNOT_CONNECT_SERVER_COMMENT))
        }
    }
}