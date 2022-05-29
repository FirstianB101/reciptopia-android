package com.ich.reciptopia.domain.use_case.post_detail

import com.ich.reciptopia.common.util.Constants
import com.ich.reciptopia.common.util.Resource
import com.ich.reciptopia.domain.model.Recipe
import com.ich.reciptopia.domain.repository.PostDetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetRecipeUseCase @Inject constructor(
    private val repository: PostDetailRepository
) {
    operator fun invoke(postId: Long): Flow<Resource<Recipe>> = flow{
        try{
            emit(Resource.Loading<Recipe>())

            val recipe = repository.getRecipe(postId)
            emit(Resource.Success<Recipe>(recipe))
        }catch (e: HttpException){
            emit(Resource.Error<Recipe>(e.localizedMessage ?: Constants.HTTP_EXCEPTION_COMMENT))
        }catch (e: IOException){
            emit(Resource.Error<Recipe>(Constants.CANNOT_CONNECT_SERVER_COMMENT))
        }
    }
}