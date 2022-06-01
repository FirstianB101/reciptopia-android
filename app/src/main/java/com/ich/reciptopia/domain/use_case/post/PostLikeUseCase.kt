package com.ich.reciptopia.domain.use_case.post

import android.database.sqlite.SQLiteException
import com.ich.reciptopia.common.util.Constants
import com.ich.reciptopia.common.util.Resource
import com.ich.reciptopia.domain.model.PostLikeTag
import com.ich.reciptopia.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PostLikeUseCase @Inject constructor(
    private val repository: PostRepository
) {
    operator fun invoke(ownerId: Long, postId: Long): Flow<Resource<PostLikeTag>> = flow{
        try{
            emit(Resource.Loading<PostLikeTag>())

            val likeTag = repository.likePost(ownerId, postId)
            emit(Resource.Success<PostLikeTag>(likeTag))
        }catch (e: HttpException){
            emit(Resource.Error<PostLikeTag>(e.localizedMessage ?: Constants.HTTP_EXCEPTION_COMMENT))
        }catch (e: IOException){
            emit(Resource.Error<PostLikeTag>(Constants.CANNOT_CONNECT_SERVER_COMMENT))
        }catch(e: SQLiteException){
            emit(Resource.Error<PostLikeTag>(Constants.SQL_EXCEPTION_COMMENT))
        }
    }
}