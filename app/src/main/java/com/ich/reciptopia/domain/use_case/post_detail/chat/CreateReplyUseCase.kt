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

class CreateReplyUseCase @Inject constructor(
    private val repository: PostDetailChatRepository
) {
    operator fun invoke(reply: Reply): Flow<Resource<Reply>> = flow{
        try{
            emit(Resource.Loading<Reply>())

            val newReply = repository.createReply(reply)
            emit(Resource.Success<Reply>(newReply))
        }catch (e: HttpException){
            emit(Resource.Error<Reply>(e.localizedMessage ?: Constants.HTTP_EXCEPTION_COMMENT))
        }catch (e: IOException){
            emit(Resource.Error<Reply>(Constants.CANNOT_CONNECT_SERVER_COMMENT))
        }
    }
}