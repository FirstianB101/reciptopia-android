package com.ich.reciptopia.domain.repository

import com.ich.reciptopia.domain.model.Account
import com.ich.reciptopia.domain.model.Post
import com.ich.reciptopia.domain.model.PostLikeTag

interface CommunityRepository {
    suspend fun getOwnerOfPost(accountId: Long): Account
    suspend fun getPostsByTime(): List<Post>
    suspend fun getPostsByViews(): List<Post>
    suspend fun createPost(post: Post): Post
    suspend fun getPostLikeTags(): List<PostLikeTag>
    suspend fun postLike(postLikeTag: PostLikeTag): PostLikeTag
}