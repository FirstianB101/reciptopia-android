package com.ich.reciptopia.domain.repository

import com.ich.reciptopia.domain.model.Account
import com.ich.reciptopia.domain.model.FavoriteEntity
import com.ich.reciptopia.domain.model.Post
import kotlinx.coroutines.flow.Flow

interface PostDetailRepository {
    suspend fun getPost(postId: Long): Post
    suspend fun getOwnerOfPost(accountId: Long): Account
    suspend fun getFavoriteEntities(): Flow<List<FavoriteEntity>>
    suspend fun favoritePostNotLogin(post: Post)
    suspend fun unFavoritePostNotLogin(post: Post)
}