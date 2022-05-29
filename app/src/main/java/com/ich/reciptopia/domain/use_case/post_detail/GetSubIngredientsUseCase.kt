package com.ich.reciptopia.domain.use_case.post_detail

import com.ich.reciptopia.common.util.Constants
import com.ich.reciptopia.common.util.Resource
import com.ich.reciptopia.domain.model.SubIngredient
import com.ich.reciptopia.domain.repository.PostDetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetSubIngredientsUseCase @Inject constructor(
    private val repository: PostDetailRepository
) {
    operator fun invoke(postId: Long): Flow<Resource<List<SubIngredient>>> = flow{
        try{
            emit(Resource.Loading<List<SubIngredient>>())

            val subIngredients = repository.getSubIngredients(postId)
            emit(Resource.Success<List<SubIngredient>>(subIngredients))
        }catch (e: HttpException){
            emit(Resource.Error<List<SubIngredient>>(e.localizedMessage ?: Constants.HTTP_EXCEPTION_COMMENT))
        }catch (e: IOException){
            emit(Resource.Error<List<SubIngredient>>(Constants.CANNOT_CONNECT_SERVER_COMMENT))
        }
    }
}