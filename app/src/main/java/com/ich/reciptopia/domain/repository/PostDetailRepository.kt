package com.ich.reciptopia.domain.repository

import com.ich.reciptopia.domain.model.Post

interface PostDetailRepository {
    suspend fun getPost(postId: Long): Post
}