package com.ich.reciptopia.domain.use_case.post_detail

import com.ich.reciptopia.common.util.Constants
import com.ich.reciptopia.common.util.Resource
import com.ich.reciptopia.domain.model.Post
import com.ich.reciptopia.domain.repository.PostDetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetPostInfoUseCase @Inject constructor(
    private val repository: PostDetailRepository
) {
    operator fun invoke(postId: Long): Flow<Resource<Post>> = flow{
        try{
            emit(Resource.Loading<Post>())

            val post = repository.getPost(postId)
            emit(Resource.Success<Post>(post))
        }catch (e: HttpException){
            emit(Resource.Error<Post>(e.localizedMessage ?: Constants.HTTP_EXCEPTION_COMMENT))
        }catch (e: IOException){
            emit(Resource.Error<Post>(Constants.CANNOT_CONNECT_SERVER_COMMENT))
        }
    }
}