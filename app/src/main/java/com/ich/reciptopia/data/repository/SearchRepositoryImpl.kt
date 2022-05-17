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

    val utils = RepositoryTestUtils()

    override fun getSearchHistoriesFromDB(): Flow<List<SearchHistory>> {
        return dao.getSearchHistories()
    }

    override suspend fun insertSearchHistoryInDB(history: SearchHistory) {
        dao.insertSearchHistory(history)
    }

    override suspend fun deleteSearchHistoryFromDB(history: SearchHistory) {
        dao.deleteSearchHistory(history)
    }

    override fun getFavoritesFromDB(): Flow<List<Favorite>> {
        return dao.getFavorites()
    }

    override suspend fun deleteFavoriteFromDB(favorite: Favorite) {
        dao.deleteFavorite(favorite)
    }

    override suspend fun getFavorites(): List<Favorite> {
        return utils.testFavorites
    }

    override suspend fun deleteFavorite(postId: Long): Response<Unit> {
        for(i in utils.testFavorites.indices){
            if(utils.testFavorites[i].id == postId) {
                utils.testFavorites.removeAt(i)
                break
            }
        }
        return Response.success(Unit)
    }

    override suspend fun getSearchHistories(userId: Long): List<SearchHistory> {
        return utils.testHistories.filter { it.ownerId == userId }
    }

    override suspend fun addSearchHistory(history: SearchHistory): SearchHistory {
        utils.testHistories.add(history)
        return history
    }

    override suspend fun deleteSearchHistory(historyId: Long): Response<Unit> {
        for(i in utils.testHistories.indices){
            if(utils.testHistories[i].id == historyId) {
                utils.testHistories.removeAt(i)
                break
            }
        }
        return Response.success(Unit)
    }

    override suspend fun getPost(postId: Long): Post {
        return utils.testPosts.find{ it.id == postId }!!
    }

    override suspend fun getOwnerOfPost(accountId: Long): Account {
        return utils.testOwner.find{it.id == accountId}!!
    }
}