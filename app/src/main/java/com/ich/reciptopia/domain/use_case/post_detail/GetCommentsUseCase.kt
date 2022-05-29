package com.ich.reciptopia.domain.use_case.post_detail

import com.ich.reciptopia.common.util.Constants
import com.ich.reciptopia.common.util.Resource
import com.ich.reciptopia.domain.model.Comment
import com.ich.reciptopia.domain.repository.PostDetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCommentsUseCase @Inject constructor(
    private val repository: PostDetailRepository
) {
    operator fun invoke(postId: Long): Flow<Resource<List<Comment>>> = flow{
        try{
            emit(Resource.Loading<List<Comment>>())

            val comment = repository.getComments(postId)
            emit(Resource.Success<List<Comment>>(comment))
        }catch (e: HttpException){
            emit(Resource.Error<List<Comment>>(e.localizedMessage ?: Constants.HTTP_EXCEPTION_COMMENT))
        }catch (e: IOException){
            emit(Resource.Error<List<Comment>>(Constants.CANNOT_CONNECT_SERVER_COMMENT))
        }
    }
}