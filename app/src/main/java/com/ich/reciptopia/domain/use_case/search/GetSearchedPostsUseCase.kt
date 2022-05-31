package com.ich.reciptopia.domain.use_case.search

import com.ich.reciptopia.common.util.Constants
import com.ich.reciptopia.common.util.Resource
import com.ich.reciptopia.domain.model.Post
import com.ich.reciptopia.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetSearchedPostsUseCase @Inject constructor(
    private val repository: SearchRepository
) {
    operator fun invoke(mainIngredients: List<String>, subIngredients: List<String>): Flow<Resource<List<Post>>> = flow{
        try{
            emit(Resource.Loading<List<Post>>())

            val posts = repository.getSearchedPosts(mainIngredients, subIngredients)
            emit(Resource.Success<List<Post>>(posts))
        }catch (e: HttpException){
            emit(Resource.Error<List<Post>>(e.localizedMessage ?: Constants.HTTP_EXCEPTION_COMMENT))
        }catch (e: IOException){
            emit(Resource.Error<List<Post>>(Constants.CANNOT_CONNECT_SERVER_COMMENT))
        }
    }
}