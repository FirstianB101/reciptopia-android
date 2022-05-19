package com.ich.reciptopia.domain.use_case.search

import android.database.sqlite.SQLiteException
import com.ich.reciptopia.common.util.Constants
import com.ich.reciptopia.common.util.Resource
import com.ich.reciptopia.domain.model.SearchHistory
import com.ich.reciptopia.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class AddSearchHistoryUseCase(
    private val repository: SearchRepository
) {
    operator fun invoke(searchHistory: SearchHistory, login: Boolean): Flow<Resource<SearchHistory>> = flow{
        try{
            emit(Resource.Loading<SearchHistory>())

            val history = if(login) repository.addSearchHistory(searchHistory)
                            else repository.insertSearchHistoryInDB(searchHistory)
            emit(Resource.Success<SearchHistory>(history))

        }catch (e: HttpException){
            emit(Resource.Error<SearchHistory>(e.localizedMessage ?: Constants.HTTP_EXCEPTION_COMMENT))
        }catch (e: IOException){
            emit(Resource.Error<SearchHistory>(Constants.CANNOT_CONNECT_SERVER_COMMENT))
        }catch(e: SQLiteException){
            emit(Resource.Error<SearchHistory>(Constants.SQL_EXCEPTION_COMMENT))
        }
    }
}