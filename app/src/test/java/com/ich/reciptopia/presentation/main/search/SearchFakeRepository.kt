package com.ich.reciptopia.presentation.main.search

import com.ich.reciptopia.domain.model.Post
import com.ich.reciptopia.domain.model.SearchHistory
import com.ich.reciptopia.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

class SearchFakeRepository: SearchRepository {
    private val histories = mutableListOf<SearchHistory>()

    override fun getSearchHistoriesFromDB(): Flow<List<SearchHistory>> {
        return flow { emit(histories) }
    }

    override suspend fun insertSearchHistoryInDB(history: SearchHistory): SearchHistory {
        histories.add(history)
        return history
    }

    override suspend fun deleteSearchHistoryFromDB(historyId: Long) {
        histories.removeIf { it.id == historyId }
    }

    override suspend fun getSearchHistories(userId: Long?): List<SearchHistory> {
        return histories
    }

    override suspend fun addSearchHistory(history: SearchHistory): SearchHistory {
        histories.add(history)
        return history
    }

    override suspend fun deleteSearchHistory(historyId: Long): Response<Unit> {
        histories.removeIf { it.id == historyId }
        return Response.success(null)
    }

    override suspend fun getSearchedPosts(
        mainIngredients: List<String>,
        subIngredients: List<String>
    ): List<Post> {
        return listOf(
            Post(
                id = 1,
                ownerId = 1,
                title = "testtitle1",
                content = "testcontent1"
            )
        )
    }

    override suspend fun getPostByIds(postIds: List<Long>): List<Post> {
        return listOf(
            Post(
                id = 1,
                ownerId = 1,
                title = "testtitle1",
                content = "testcontent1"
            )
        )
    }
}