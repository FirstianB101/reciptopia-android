package com.ich.reciptopia.data.repository

import com.ich.reciptopia.data.data_source.SearchDao
import com.ich.reciptopia.data.remote.ReciptopiaApi
import com.ich.reciptopia.domain.model.Account
import com.ich.reciptopia.domain.model.Favorite
import com.ich.reciptopia.domain.model.Post
import com.ich.reciptopia.domain.repository.PostDetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class PostDetailRepositoryImpl(
    private val api: ReciptopiaApi,
    private val dao: SearchDao
): PostDetailRepository {

    val utils = RepositoryTestUtils()

    override suspend fun getPost(postId: Long): Post {
        return utils.testPosts.find { it.id == postId }!!
    }

    override suspend fun getOwnerOfPost(accountId: Long): Account {
        return utils.testOwner.find { it.id == accountId }!!
    }

    override suspend fun favoritePostNotLogin(postId: Long) {
        dao.insertFavorite(Favorite(postId = postId))
    }

    override suspend fun unFavoritePostNotLogin(postId: Long) {
        val entities = dao.getFavorites().first()
        val entity = entities.find{ it.postId == postId }
        if (entity != null) {
            dao.deleteFavorite(entity)
        }
    }

    override suspend fun getFavoritesFromDB(): Flow<List<Favorite>> {
        return dao.getFavorites()
    }

    override suspend fun getFavorites(userId: Long): List<Favorite> {
        return utils.testFavorites.filter {it.ownerId == userId}
    }
}