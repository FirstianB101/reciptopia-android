package com.ich.reciptopia.domain.use_case.post

import android.database.sqlite.SQLiteException
import com.ich.reciptopia.common.util.Constants
import com.ich.reciptopia.common.util.Resource
import com.ich.reciptopia.domain.model.Favorite
import com.ich.reciptopia.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class FavoritePostUseCase @Inject constructor(
    private val repository: PostRepository
){
    operator fun invoke(ownerId: Long?, postId: Long?, login: Boolean): Flow<Resource<Favorite>> = flow{
        try{
            emit(Resource.Loading<Favorite>())

            val favorite = if(login) repository.favoritePostLogin(ownerId, postId)
            else repository.favoritePostNotLogin(ownerId, postId)

            emit(Resource.Success<Favorite>(favorite))
        }catch (e: HttpException){
            emit(Resource.Error<Favorite>(e.localizedMessage ?: Constants.HTTP_EXCEPTION_COMMENT))
        }catch (e: IOException){
            emit(Resource.Error<Favorite>(Constants.CANNOT_CONNECT_SERVER_COMMENT))
        }catch(e: SQLiteException){
            emit(Resource.Error<Favorite>(Constants.SQL_EXCEPTION_COMMENT))
        }
    }
}