package com.ich.reciptopia.domain.use_case.post_detail.chat

import com.ich.reciptopia.common.util.Constants
import com.ich.reciptopia.common.util.Resource
import com.ich.reciptopia.domain.model.Comment
import com.ich.reciptopia.domain.repository.PostDetailChatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CreateCommentUseCase @Inject constructor(
    private val repository: PostDetailChatRepository
) {
    operator fun invoke(comment: Comment): Flow<Resource<Comment>> = flow{
        try{
            emit(Resource.Loading<Comment>())

            val newComment = repository.createComment(comment)
            emit(Resource.Success<Comment>(newComment))
        }catch (e: HttpException){
            emit(Resource.Error<Comment>(e.localizedMessage ?: Constants.HTTP_EXCEPTION_COMMENT))
        }catch (e: IOException){
            emit(Resource.Error<Comment>(Constants.CANNOT_CONNECT_SERVER_COMMENT))
        }
    }
}