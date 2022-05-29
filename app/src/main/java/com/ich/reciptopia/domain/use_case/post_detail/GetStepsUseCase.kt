package com.ich.reciptopia.domain.use_case.post_detail

import com.ich.reciptopia.common.util.Constants
import com.ich.reciptopia.common.util.Resource
import com.ich.reciptopia.domain.model.Step
import com.ich.reciptopia.domain.repository.PostDetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetStepsUseCase @Inject constructor(
    private val repository: PostDetailRepository
) {
    operator fun invoke(recipeId: Long): Flow<Resource<List<Step>>> = flow{
        try{
            emit(Resource.Loading<List<Step>>())

            val steps = repository.getSteps(recipeId)
            emit(Resource.Success<List<Step>>(steps))
        }catch (e: HttpException){
            emit(Resource.Error<List<Step>>(e.localizedMessage ?: Constants.HTTP_EXCEPTION_COMMENT))
        }catch (e: IOException){
            emit(Resource.Error<List<Step>>(Constants.CANNOT_CONNECT_SERVER_COMMENT))
        }
    }
}