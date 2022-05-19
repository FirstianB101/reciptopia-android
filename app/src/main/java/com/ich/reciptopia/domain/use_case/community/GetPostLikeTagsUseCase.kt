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

class GetPostLikeTagsUseCase @Inject constructor(
    private val repository: CommunityRepository
) {
    operator fun invoke(userId: Long): Flow<Resource<List<PostLikeTag>>> = flow{
        try{
            emit(Resource.Loading<List<PostLikeTag>>())

            val tags = repository.getLikeTags(userId)
            emit(Resource.Success<List<PostLikeTag>>(tags))
        }catch (e: HttpException){
            emit(Resource.Error<List<PostLikeTag>>(e.localizedMessage ?: Constants.HTTP_EXCEPTION_COMMENT))
        }catch (e: IOException){
            emit(Resource.Error<List<PostLikeTag>>(Constants.CANNOT_CONNECT_SERVER_COMMENT))
        }
    }
}