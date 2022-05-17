package com.ich.reciptopia.domain.use_case.search

import com.ich.reciptopia.common.util.Constants
import com.ich.reciptopia.common.util.Resource
import com.ich.reciptopia.domain.model.SearchHistory
import com.ich.reciptopia.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class GetSearchHistoriesUseCase(
    private val repository: SearchRepository
) {
    operator fun invoke(userId: Long): Flow<Resource<List<SearchHistory>>> = flow{
        try{
            emit(Resource.Loading<List<SearchHistory>>())

            val histories = repository.getSearchHistories(userId)
            emit(Resource.Success<List<SearchHistory>>(histories))

        }catch (e: HttpException){
            emit(Resource.Error<List<SearchHistory>>(e.localizedMessage ?: Constants.HTTP_EXCEPTION_COMMENT))
        }catch (e: IOException){
            emit(Resource.Error<List<SearchHistory>>(Constants.CANNOT_CONNECT_SERVER_COMMENT))
        }
    }
}