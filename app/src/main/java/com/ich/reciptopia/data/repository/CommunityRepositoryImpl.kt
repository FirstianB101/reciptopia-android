package com.ich.reciptopia.data.repository

import com.ich.reciptopia.data.remote.ReciptopiaApi
import com.ich.reciptopia.data.repository.RepositoryTestUtils.nextPostId
import com.ich.reciptopia.domain.model.Post
import com.ich.reciptopia.domain.repository.CommunityRepository
import kotlinx.coroutines.delay

class CommunityRepositoryImpl(
    private val api: ReciptopiaApi
): CommunityRepository {

    override suspend fun getPostsByTime(searchQuery: String): List<Post> {
        delay(500L)
        return RepositoryTestUtils.testPosts.filter { it.title?.contains(searchQuery) ?: false }
    }

    override suspend fun getPostsByViews(searchQuery: String): List<Post> {
        delay(500L)
        return RepositoryTestUtils.testPosts.reversed().filter{ it.title?.contains(searchQuery) ?: false }
    }

    override suspend fun createPost(post: Post): Post {
        delay(500L)
        RepositoryTestUtils.testPosts.add(
            post.copy(
                id = nextPostId++
            )
        )
        return post
    }
}