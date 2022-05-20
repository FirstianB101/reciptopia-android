package com.ich.reciptopia.domain.repository

import com.ich.reciptopia.domain.model.Account
import com.ich.reciptopia.domain.model.Favorite
import com.ich.reciptopia.domain.model.PostLikeTag
import kotlinx.coroutines.flow.Flow

interface PostListRepository{
    suspend fun getOwnerOfPost(accountId: Long): Account

    suspend fun favoritePostNotLogin(ownerId: Long?, postId: Long?)
    suspend fun favoritePostLogin(ownerId: Long?, postId: Long?)
    suspend fun unFavoritePostNotLogin(postId: Long)
    suspend fun unFavoritePostLogin(ownerId: Long?, postId: Long?)
    suspend fun getFavoritesFromDB(): Flow<List<Favorite>>
    suspend fun getFavorites(userId: Long): List<Favorite>

    suspend fun likePost(ownerId: Long?, postId: Long?)
    suspend fun unLikePost(ownerId: Long?, postId: Long?)
    suspend fun getLikeTags(userId: Long): List<PostLikeTag>
}