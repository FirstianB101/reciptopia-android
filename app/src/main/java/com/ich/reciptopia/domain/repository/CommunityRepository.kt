package com.ich.reciptopia.domain.repository

import com.ich.reciptopia.domain.model.Account
import com.ich.reciptopia.domain.model.FavoriteEntity
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

    suspend fun favoritePost(post: Post)
    suspend fun getFavoriteEntities(): Flow<List<FavoriteEntity>>
}