package com.ich.reciptopia.domain.repository

import com.ich.reciptopia.domain.model.Post
import com.ich.reciptopia.domain.model.SearchHistory
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface SearchRepository {

    fun getSearchHistoriesFromDB(): Flow<List<SearchHistory>>
    suspend fun insertSearchHistoryInDB(history: SearchHistory): SearchHistory
    suspend fun deleteSearchHistoryFromDB(historyId: Long)
    suspend fun getSearchHistories(userId: Long?): List<SearchHistory>
    suspend fun addSearchHistory(history: SearchHistory): SearchHistory
    suspend fun deleteSearchHistory(historyId: Long): Response<Unit>

    suspend fun getSearchedPosts(mainIngredients: List<String>, subIngredients: List<String>): List<Post>

    suspend fun getPostByIds(postIds: List<Long>): List<Post>
}