package com.ich.reciptopia.domain.use_case.post_detail.chat

import com.ich.reciptopia.common.util.Constants
import com.ich.reciptopia.common.util.Resource
import com.ich.reciptopia.domain.model.Reply
import com.ich.reciptopia.domain.repository.PostDetailChatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetRepliesUseCase @Inject constructor(
    private val repository: PostDetailChatRepository
) {
    operator fun invoke(commentId: Long): Flow<Resource<List<Reply>>> = flow{
        try{
            emit(Resource.Loading<List<Reply>>())

            val replies = repository.getReplies(commentId)
            emit(Resource.Success<List<Reply>>(replies))
        }catch (e: HttpException){
            emit(Resource.Error<List<Reply>>(e.localizedMessage ?: Constants.HTTP_EXCEPTION_COMMENT))
        }catch (e: IOException){
            emit(Resource.Error<List<Reply>>(Constants.CANNOT_CONNECT_SERVER_COMMENT))
        }
    }
}