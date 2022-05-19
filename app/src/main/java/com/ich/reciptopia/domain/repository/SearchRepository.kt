package com.ich.reciptopia.domain.repository

import com.ich.reciptopia.domain.model.Account
import com.ich.reciptopia.domain.model.Favorite
import com.ich.reciptopia.domain.model.Post
import com.ich.reciptopia.domain.model.SearchHistory
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface SearchRepository {

    fun getSearchHistoriesFromDB(): Flow<List<SearchHistory>>
    suspend fun insertSearchHistoryInDB(history: SearchHistory): SearchHistory
    suspend fun deleteSearchHistoryFromDB(historyId: Long)

    fun getFavoritesFromDB(): Flow<List<Favorite>>
    suspend fun deleteFavoriteFromDB(postId: Long)

    suspend fun getFavorites(userId: Long?): List<Favorite>
    suspend fun deleteFavorite(postId: Long): Response<Unit>

    suspend fun getSearchHistories(userId: Long?): List<SearchHistory>
    suspend fun addSearchHistory(history: SearchHistory): SearchHistory
    suspend fun deleteSearchHistory(historyId: Long): Response<Unit>

    suspend fun getSearchedPosts(): List<Post>
    suspend fun favoritePostNotLogin(ownerId: Long?, postId: Long?)
    suspend fun favoritePostLogin(ownerId: Long?, postId: Long?)
    suspend fun unFavoritePostNotLogin(postId: Long)
    suspend fun unFavoritePostLogin(ownerId: Long?, postId: Long?)
    suspend fun getFavorites(userId: Long): List<Favorite>

    suspend fun getPost(postId: Long): Post
    suspend fun getOwnerOfPost(accountId: Long): Account
}