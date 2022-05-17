package com.ich.reciptopia.domain.repository

import com.ich.reciptopia.domain.model.Account
import com.ich.reciptopia.domain.model.Favorite
import com.ich.reciptopia.domain.model.Post
import kotlinx.coroutines.flow.Flow

interface PostDetailRepository {
    suspend fun getPost(postId: Long): Post
    suspend fun getOwnerOfPost(accountId: Long): Account
    suspend fun getFavoritesFromDB(): Flow<List<Favorite>>
    suspend fun getFavorites(userId: Long): List<Favorite>
    suspend fun favoritePostNotLogin(postId: Long)
    suspend fun unFavoritePostNotLogin(postId: Long)
}