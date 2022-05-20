package com.ich.reciptopia.data.repository

import com.ich.reciptopia.data.data_source.ReciptopiaDao
import com.ich.reciptopia.data.remote.ReciptopiaApi
import com.ich.reciptopia.domain.model.Account
import com.ich.reciptopia.domain.model.Favorite
import com.ich.reciptopia.domain.model.PostLikeTag
import com.ich.reciptopia.domain.repository.PostListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class PostListRepositoryImpl(
    private val api: ReciptopiaApi,
    private val dao: ReciptopiaDao
) : PostListRepository {

    override suspend fun getOwnerOfPost(accountId: Long): Account {
        return RepositoryTestUtils.testOwner.find { it.id == accountId }!!
    }

    override suspend fun favoritePostLogin(ownerId: Long?, postId: Long?) {
        val favorite = Favorite(
            id = RepositoryTestUtils.nextFavoriteId++,
            ownerId = ownerId,
            postId = postId
        )
        RepositoryTestUtils.testFavorites.add(favorite)
    }

    override suspend fun unFavoritePostLogin(ownerId: Long?, postId: Long?) {
        for (i in RepositoryTestUtils.testFavorites.indices) {
            if (RepositoryTestUtils.testFavorites[i].ownerId == ownerId && RepositoryTestUtils.testFavorites[i].postId == postId) {
                RepositoryTestUtils.testFavorites.removeAt(i)
                break
            }
        }
    }

    override suspend fun favoritePostNotLogin(ownerId: Long?, postId: Long?) {
        dao.insertFavorite(
            Favorite(
                id = RepositoryTestUtils.nextFavoriteId++,
                postId = postId,
                ownerId = ownerId
            )
        )
    }

    override suspend fun unFavoritePostNotLogin(postId: Long) {
        val favorites = dao.getFavorites().first()
        val favorite = favorites.find { it.postId == postId }
        if (favorite != null) {
            dao.deleteFavorite(favorite)
        }
    }

    override suspend fun getFavoritesFromDB(): Flow<List<Favorite>> {
        return dao.getFavorites()
    }

    override suspend fun getFavorites(userId: Long): List<Favorite> {
        return RepositoryTestUtils.testFavorites.filter { it.ownerId == userId }
    }

    override suspend fun likePost(ownerId: Long?, postId: Long?) {
        RepositoryTestUtils.testPostLikeTags.add(
            PostLikeTag(
                id = RepositoryTestUtils.nextPostId++,
                ownerId = ownerId,
                postId = postId
            )
        )
    }

    override suspend fun unLikePost(ownerId: Long?, postId: Long?) {
        for (i in RepositoryTestUtils.testPostLikeTags.indices) {
            if (RepositoryTestUtils.testPostLikeTags[i].ownerId == ownerId &&
                RepositoryTestUtils.testPostLikeTags[i].postId == postId
            ) {
                RepositoryTestUtils.testPostLikeTags.removeAt(i)
                break
            }
        }
    }


    override suspend fun getLikeTags(userId: Long): List<PostLikeTag> {
        return RepositoryTestUtils.testPostLikeTags.filter { it.ownerId == userId }
    }
}