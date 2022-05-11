package com.ich.reciptopia.domain.use_case.community

import com.ich.reciptopia.common.util.Constants
import com.ich.reciptopia.common.util.Resource
import com.ich.reciptopia.domain.model.PostLikeTag
import com.ich.reciptopia.domain.repository.CommunityRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PostLikeUseCase @Inject constructor(
    private val repository: CommunityRepository
) {
    operator fun invoke(postLikeTag: PostLikeTag): Flow<Resource<PostLikeTag>> = flow{
        try{
            emit(Resource.Loading<PostLikeTag>())

            val newTag = repository.postLike(postLikeTag)
            emit(Resource.Success<PostLikeTag>(newTag))
        }catch (e: HttpException){
            emit(Resource.Error<PostLikeTag>(e.localizedMessage ?: Constants.HTTP_EXCEPTION_COMMENT))
        }catch (e: IOException){
            emit(Resource.Error<PostLikeTag>(Constants.CANNOT_CONNECT_SERVER_COMMENT))
        }
    }
}