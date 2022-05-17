package com.ich.reciptopia.domain.repository

import com.ich.reciptopia.domain.model.Account
import com.ich.reciptopia.domain.model.Favorite
import com.ich.reciptopia.domain.model.Post
import com.ich.reciptopia.domain.model.SearchHistory
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface SearchRepository {

    fun getSearchHistoriesFromDB(): Flow<List<SearchHistory>>
    suspend fun insertSearchHistoryInDB(history: SearchHistory)
    suspend fun deleteSearchHistoryFromDB(history: SearchHistory)

    fun getFavoritesFromDB(): Flow<List<Favorite>>
    suspend fun deleteFavoriteFromDB(favorite: Favorite)

    suspend fun getFavorites(): List<Favorite>
    suspend fun deleteFavorite(postId: Long): Response<Unit>

    suspend fun getSearchHistories(userId: Long): List<SearchHistory>
    suspend fun addSearchHistory(history: SearchHistory): SearchHistory
    suspend fun deleteSearchHistory(historyId: Long): Response<Unit>

    suspend fun getPost(postId: Long): Post
    suspend fun getOwnerOfPost(accountId: Long): Account
}