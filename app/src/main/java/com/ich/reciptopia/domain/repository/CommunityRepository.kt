package com.ich.reciptopia.domain.repository

import com.ich.reciptopia.domain.model.Post

interface CommunityRepository {
    suspend fun getPostsByTime(): List<Post>
    suspend fun getPostsByViews(): List<Post>
    suspend fun createPost(post: Post): Post
}