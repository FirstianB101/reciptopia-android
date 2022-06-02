package com.ich.reciptopia.domain.use_case.post_detail.chat

import com.ich.reciptopia.common.util.Constants
import com.ich.reciptopia.common.util.Resource
import com.ich.reciptopia.domain.repository.PostDetailChatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class DeleteCommentUseCase @Inject constructor(
    private val repository: PostDetailChatRepository
) {
    operator fun invoke(commentId: Long): Flow<Resource<Unit>> = flow{
        try{
            emit(Resource.Loading<Unit>())

            repository.deleteComment(commentId)
            emit(Resource.Success<Unit>(null))
        }catch (e: HttpException){
            emit(Resource.Error<Unit>(e.localizedMessage ?: Constants.HTTP_EXCEPTION_COMMENT))
        }catch (e: IOException){
            emit(Resource.Error<Unit>(Constants.CANNOT_CONNECT_SERVER_COMMENT))
        }
    }
}