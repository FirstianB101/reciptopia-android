package com.ich.reciptopia.domain.repository

import com.ich.reciptopia.domain.model.Account
import com.ich.reciptopia.domain.model.Favorite
import com.ich.reciptopia.domain.model.Post
import com.ich.reciptopia.domain.model.PostLikeTag
import kotlinx.coroutines.flow.Flow

interface CommunityRepository {
    suspend fun getOwnerOfPost(accountId: Long): Account
    suspend fun getPostsByTime(): List<Post>
    suspend fun getPostsByViews(): List<Post>
    suspend fun createPost(post: Post): Post
    suspend fun getPostLikeTags(): List<PostLikeTag>
    suspend fun postLike(postLikeTag: PostLikeTag): PostLikeTag

    suspend fun favoritePostNotLogin(postId: Long)
    suspend fun unFavoritePostNotLogin(postId: Long)
    suspend fun getFavoritesFromDB(): Flow<List<Favorite>>
    suspend fun getFavorites(userId: Long): List<Favorite>
}