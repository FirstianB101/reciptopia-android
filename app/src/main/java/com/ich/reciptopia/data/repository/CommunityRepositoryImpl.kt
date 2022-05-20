package com.ich.reciptopia.data.repository

import com.ich.reciptopia.data.remote.ReciptopiaApi
import com.ich.reciptopia.data.repository.RepositoryTestUtils.nextPostId
import com.ich.reciptopia.domain.model.Post
import com.ich.reciptopia.domain.repository.CommunityRepository

class CommunityRepositoryImpl(
    private val api: ReciptopiaApi
): CommunityRepository {

    override suspend fun getPostsByTime(): List<Post> {
        return RepositoryTestUtils.testPosts
    }

    override suspend fun getPostsByViews(): List<Post> {
        return RepositoryTestUtils.testPosts.reversed()
    }

    override suspend fun createPost(post: Post): Post {
        RepositoryTestUtils.testPosts.add(
            post.copy(
                id = nextPostId++
            )
        )
        return post
    }
}