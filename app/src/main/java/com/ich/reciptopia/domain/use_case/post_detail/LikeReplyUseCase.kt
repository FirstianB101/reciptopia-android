package com.ich.reciptopia.domain.use_case.post_detail

import com.ich.reciptopia.common.util.Constants
import com.ich.reciptopia.common.util.Resource
import com.ich.reciptopia.domain.model.ReplyLikeTag
import com.ich.reciptopia.domain.repository.PostDetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class LikeReplyUseCase @Inject constructor(
    private val repository: PostDetailRepository
) {
    operator fun invoke(ownerId: Long?, replyId: Long): Flow<Resource<ReplyLikeTag>> = flow{
        try{
            emit(Resource.Loading<ReplyLikeTag>())

            val newTag = repository.likeReply(ownerId, replyId)
            emit(Resource.Success<ReplyLikeTag>(newTag))
        }catch (e: HttpException){
            emit(Resource.Error<ReplyLikeTag>(e.localizedMessage ?: Constants.HTTP_EXCEPTION_COMMENT))
        }catch (e: IOException){
            emit(Resource.Error<ReplyLikeTag>(Constants.CANNOT_CONNECT_SERVER_COMMENT))
        }
    }
}