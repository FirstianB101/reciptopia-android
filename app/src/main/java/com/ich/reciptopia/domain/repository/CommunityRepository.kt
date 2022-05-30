package com.ich.reciptopia.domain.repository

import com.ich.reciptopia.domain.model.Post

interface CommunityRepository {
    suspend fun getPostsByTime(searchQuery: String): List<Post>
    suspend fun getPostsByViews(searchQuery: String): List<Post>
    suspend fun createPost(post: Post): Post
}