package com.ich.reciptopia.data.repository

import com.ich.reciptopia.data.data_source.ReciptopiaDao
import com.ich.reciptopia.data.remote.ReciptopiaApi
import com.ich.reciptopia.domain.model.Post
import com.ich.reciptopia.domain.repository.PostDetailRepository

class PostDetailRepositoryImpl(
    private val api: ReciptopiaApi
): PostDetailRepository {

    override suspend fun getPost(postId: Long): Post {
        return RepositoryTestUtils.testPosts.find { it.id == postId }!!
    }
}