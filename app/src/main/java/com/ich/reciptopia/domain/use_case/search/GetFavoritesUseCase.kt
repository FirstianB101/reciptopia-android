package com.ich.reciptopia.domain.use_case.search

import android.database.sqlite.SQLiteException
import com.ich.reciptopia.common.util.Constants
import com.ich.reciptopia.common.util.Resource
import com.ich.reciptopia.domain.model.Favorite
import com.ich.reciptopia.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class GetFavoritesUseCase(
    private val repository: SearchRepository
) {
    operator fun invoke(userId: Long?): Flow<Resource<List<Favorite>>> = flow{
        try{
            emit(Resource.Loading<List<Favorite>>())

            val favorites = if(userId != null) repository.getFavorites(userId)
                            else repository.getFavoritesFromDB().first()
            emit(Resource.Success<List<Favorite>>(favorites))

        }catch (e: HttpException){
            emit(Resource.Error<List<Favorite>>(e.localizedMessage ?: Constants.HTTP_EXCEPTION_COMMENT))
        }catch (e: IOException){
            emit(Resource.Error<List<Favorite>>(Constants.CANNOT_CONNECT_SERVER_COMMENT))
        }catch(e: SQLiteException){
            emit(Resource.Error<List<Favorite>>(Constants.SQL_EXCEPTION_COMMENT))
        }
    }
}