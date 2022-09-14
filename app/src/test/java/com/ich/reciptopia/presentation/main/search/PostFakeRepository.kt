package com.ich.reciptopia.presentation.main.search

import android.graphics.Bitmap
import com.ich.reciptopia.domain.model.Account
import com.ich.reciptopia.domain.model.Favorite
import com.ich.reciptopia.domain.model.PostLikeTag
import com.ich.reciptopia.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PostFakeRepository: PostRepository {
    private val favorites = mutableListOf<Favorite>()
    private val likeTags = mutableListOf<PostLikeTag>()
    private var favoriteId = 0L
    private var likeTagId = 0L

    override suspend fun getOwnerOfPost(accountId: Long): Account {
        return Account(
            role = null,
            id = 1,
            email = "test@gmail.com",
            nickname = "testnick"
        )
    }

    override suspend fun favoritePostNotLogin(ownerId: Long?, postId: Long?): Favorite {
        val newFavorite = Favorite(favoriteId++, ownerId, postId)
        favorites.add(newFavorite)
        return newFavorite
    }

    override suspend fun favoritePostLogin(ownerId: Long?, postId: Long?): Favorite {
        val newFavorite = Favorite(favoriteId++, ownerId, postId)
        favorites.add(newFavorite)
        return newFavorite
    }

    override suspend fun unFavoritePostNotLogin(postId: Long) {
        favorites.removeIf { it.postId == postId }
    }

    override suspend fun unFavoritePostLogin(favoriteId: Long?) {
        favorites.removeIf { it.id == favoriteId }
    }

    override suspend fun getFavoritesFromDB(): Flow<List<Favorite>> {
        return flow{
            emit(favorites)
        }
    }

    override suspend fun getFavorites(userId: Long): List<Favorite> {
        return favorites
    }

    override suspend fun likePost(ownerId: Long?, postId: Long?): PostLikeTag {
        val newLikeTag = PostLikeTag(likeTagId++, ownerId, postId)
        likeTags.add(newLikeTag)
        return newLikeTag
    }

    override suspend fun unLikePost(postLikeTagId: Long) {
        likeTags.removeIf { it.id == postLikeTagId }
    }

    override suspend fun getLikeTags(userId: Long): List<PostLikeTag> {
        return likeTags
    }

    override suspend fun getOwnerProfileImage(ownerId: Long): Bitmap? {
        return null
    }
}