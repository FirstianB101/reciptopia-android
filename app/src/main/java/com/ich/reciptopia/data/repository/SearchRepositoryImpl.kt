package com.ich.reciptopia.data.repository

import com.ich.reciptopia.data.data_source.ReciptopiaDao
import com.ich.reciptopia.data.remote.ReciptopiaApi
import com.ich.reciptopia.data.remote.dto.toPostList
import com.ich.reciptopia.data.remote.dto.toSearchHistory
import com.ich.reciptopia.data.remote.dto.toSearchHistoryList
import com.ich.reciptopia.domain.model.Post
import com.ich.reciptopia.domain.model.SearchHistory
import com.ich.reciptopia.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class SearchRepositoryImpl(
    private val api: ReciptopiaApi,
    private val dao: ReciptopiaDao
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

    override suspend fun getSearchHistories(userId: Long?): List<SearchHistory> {
        return api.getSearchHistories(userId).toSearchHistoryList()
    }

    override suspend fun addSearchHistory(history: SearchHistory): SearchHistory {
        return api.createSearchHistory(history).toSearchHistory()
    }

    override suspend fun deleteSearchHistory(historyId: Long): Response<Unit> {
        return api.deleteSearchHistory(historyId)
    }

    override suspend fun getPostByIds(postIds: List<Long>): List<Post> {
        return api.getPostsByIds(postIds).toPostList()
    }

    override suspend fun getSearchedPosts(
        mainIngredients: List<String>,
        subIngredients: List<String>
    ): List<Post> {
        return api.getPostsByIngredients(mainIngredients, subIngredients).toPostList()
    }
}