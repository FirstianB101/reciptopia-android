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

class LikeCommentUseCase @Inject constructor(
    private val repository: PostDetailChatRepository
) {
    operator fun invoke(ownerId: Long?, commentId: Long): Flow<Resource<CommentLikeTag>> = flow{
        try{
            emit(Resource.Loading<CommentLikeTag>())

            val newTag = repository.likeComment(ownerId, commentId)
            emit(Resource.Success<CommentLikeTag>(newTag))
        }catch (e: HttpException){
            emit(Resource.Error<CommentLikeTag>(e.localizedMessage ?: Constants.HTTP_EXCEPTION_COMMENT))
        }catch (e: IOException){
            emit(Resource.Error<CommentLikeTag>(Constants.CANNOT_CONNECT_SERVER_COMMENT))
        }
    }
}