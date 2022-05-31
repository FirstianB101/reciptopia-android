package com.ich.reciptopia.data.repository

import com.ich.reciptopia.data.data_source.ReciptopiaDao
import com.ich.reciptopia.data.remote.ReciptopiaApi
import com.ich.reciptopia.data.remote.dto.toAccount
import com.ich.reciptopia.data.remote.dto.toFavorite
import com.ich.reciptopia.data.remote.dto.toFavoriteList
import com.ich.reciptopia.data.remote.dto.toPostLikeTag
import com.ich.reciptopia.domain.model.Account
import com.ich.reciptopia.domain.model.Favorite
import com.ich.reciptopia.domain.model.PostLikeTag
import com.ich.reciptopia.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class PostRepositoryImpl(
    private val api: ReciptopiaApi,
    private val dao: ReciptopiaDao
) : PostRepository {

    override suspend fun getOwnerOfPost(accountId: Long): Account {
        return api.getAccount(accountId).toAccount()
    }

    override suspend fun favoritePostLogin(ownerId: Long?, postId: Long?): Favorite {
        return api.createFavorite(Favorite(ownerId = ownerId, postId = postId)).toFavorite()
    }

    override suspend fun unFavoritePostLogin(favoriteId: Long?) {
        api.deleteFavorite(favoriteId)
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
        return api.getFavoritesByOwnerId(userId).toFavoriteList()
    }

    override suspend fun likePost(ownerId: Long?, postId: Long?): PostLikeTag {
        return api.createPostLikeTag(PostLikeTag(ownerId = ownerId, postId = postId)).toPostLikeTag()
    }

    override suspend fun unLikePost(postLikeTagId: Long?) {
        api.deletePostLikeTag(postLikeTagId)
    }


    override suspend fun getLikeTags(userId: Long): List<PostLikeTag> {
        return api.getPostLikeTags().map{it.toPostLikeTag()}
    }
}