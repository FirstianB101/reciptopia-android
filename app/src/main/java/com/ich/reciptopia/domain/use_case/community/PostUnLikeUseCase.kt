package com.ich.reciptopia.domain.use_case.community

import android.database.sqlite.SQLiteException
import com.ich.reciptopia.common.util.Constants
import com.ich.reciptopia.common.util.Resource
import com.ich.reciptopia.domain.repository.CommunityRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PostUnLikeUseCase @Inject constructor(
    private val repository: CommunityRepository
) {
    operator fun invoke(ownerId: Long, postId: Long,): Flow<Resource<Unit>> = flow{
        try{
            emit(Resource.Loading<Unit>())

            repository.unLikePost(ownerId, postId)

            emit(Resource.Success<Unit>(null))
        }catch (e: HttpException){
            emit(Resource.Error<Unit>(e.localizedMessage ?: Constants.HTTP_EXCEPTION_COMMENT))
        }catch (e: IOException){
            emit(Resource.Error<Unit>(Constants.CANNOT_CONNECT_SERVER_COMMENT))
        }catch(e: SQLiteException){
            emit(Resource.Error<Unit>(Constants.SQL_EXCEPTION_COMMENT))
        }
    }
}