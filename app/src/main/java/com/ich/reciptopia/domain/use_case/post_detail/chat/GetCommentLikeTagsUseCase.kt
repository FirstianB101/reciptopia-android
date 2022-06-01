package com.ich.reciptopia.domain.use_case.post_detail.chat

import com.ich.reciptopia.common.util.Constants
import com.ich.reciptopia.common.util.Resource
import com.ich.reciptopia.domain.model.CommentLikeTag
import com.ich.reciptopia.domain.repository.PostDetailChatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCommentLikeTagsUseCase @Inject constructor(
    private val repository: PostDetailChatRepository
) {
    operator fun invoke(ownerId: Long?): Flow<Resource<List<CommentLikeTag>>> = flow{
        try{
            emit(Resource.Loading<List<CommentLikeTag>>())

            val tags = repository.getCommentLikeTags(ownerId)
            emit(Resource.Success<List<CommentLikeTag>>(tags))
        }catch (e: HttpException){
            emit(Resource.Error<List<CommentLikeTag>>(e.localizedMessage ?: Constants.HTTP_EXCEPTION_COMMENT))
        }catch (e: IOException){
            emit(Resource.Error<List<CommentLikeTag>>(Constants.CANNOT_CONNECT_SERVER_COMMENT))
        }
    }
}