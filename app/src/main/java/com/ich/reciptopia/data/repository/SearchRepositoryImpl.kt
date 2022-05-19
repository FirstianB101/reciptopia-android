package com.ich.reciptopia.data.repository

import com.ich.reciptopia.data.data_source.SearchDao
import com.ich.reciptopia.data.remote.ReciptopiaApi
import com.ich.reciptopia.domain.model.Account
import com.ich.reciptopia.domain.model.Favorite
import com.ich.reciptopia.domain.model.Post
import com.ich.reciptopia.domain.model.SearchHistory
import com.ich.reciptopia.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class SearchRepositoryImpl(
    private val api: ReciptopiaApi,
    private val dao: SearchDao
): SearchRepository {

    override fun getSearchHistoriesFromDB(): Flow<List<SearchHistory>> {
        return dao.getSearchHistories()
    }

    override suspend fun insertSearchHistoryInDB(history: SearchHistory): SearchHistory {
        dao.insertSearchHistory(history)
        return history
    }

    override suspend fun deleteSearchHistoryFromDB(historyId: Long) {
        dao.deleteSearchHistory(historyId)
    }

    override fun getFavoritesFromDB(): Flow<List<Favorite>> {
        return dao.getFavorites()
    }

    override suspend fun deleteFavoriteFromDB(postId: Long) {
        dao.deleteFavorite(postId)
    }

    override suspend fun getFavorites(userId: Long?): List<Favorite> {
        return RepositoryTestUtils.testFavorites.filter{ it.ownerId == userId }
    }

    override suspend fun deleteFavorite(postId: Long): Response<Unit> {
        for(i in RepositoryTestUtils.testFavorites.indices){
            if(RepositoryTestUtils.testFavorites[i].id == postId) {
                RepositoryTestUtils.testFavorites.removeAt(i)
                break
            }
        }
        return Response.success(Unit)
    }

    override suspend fun getSearchHistories(userId: Long?): List<SearchHistory> {
        return RepositoryTestUtils.testHistories.filter { it.ownerId == userId }
    }

    override suspend fun addSearchHistory(history: SearchHistory): SearchHistory {
        RepositoryTestUtils.testHistories.add(history)
        return history
    }

    override suspend fun deleteSearchHistory(historyId: Long): Response<Unit> {
        for(i in RepositoryTestUtils.testHistories.indices){
            if(RepositoryTestUtils.testHistories[i].id == historyId) {
                RepositoryTestUtils.testHistories.removeAt(i)
                break
            }
        }
        return Response.success(Unit)
    }

    override suspend fun getPost(postId: Long): Post {
        return RepositoryTestUtils.testPosts.find{ it.id == postId }!!
    }

    override suspend fun getOwnerOfPost(accountId: Long): Account {
        return RepositoryTestUtils.testOwner.find{it.id == accountId}!!
    }
}