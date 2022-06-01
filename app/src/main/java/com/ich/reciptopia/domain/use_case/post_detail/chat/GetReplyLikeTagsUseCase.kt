package com.ich.reciptopia.domain.use_case.post_detail.chat

import com.ich.reciptopia.common.util.Constants
import com.ich.reciptopia.common.util.Resource
import com.ich.reciptopia.domain.model.ReplyLikeTag
import com.ich.reciptopia.domain.repository.PostDetailChatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetReplyLikeTagsUseCase @Inject constructor(
    private val repository: PostDetailChatRepository
) {
    operator fun invoke(ownerId: Long?): Flow<Resource<List<ReplyLikeTag>>> = flow{
        try{
            emit(Resource.Loading<List<ReplyLikeTag>>())

            val tags = repository.getReplyLikeTags(ownerId)
            emit(Resource.Success<List<ReplyLikeTag>>(tags))
        }catch (e: HttpException){
            emit(Resource.Error<List<ReplyLikeTag>>(e.localizedMessage ?: Constants.HTTP_EXCEPTION_COMMENT))
        }catch (e: IOException){
            emit(Resource.Error<List<ReplyLikeTag>>(Constants.CANNOT_CONNECT_SERVER_COMMENT))
        }
    }
}