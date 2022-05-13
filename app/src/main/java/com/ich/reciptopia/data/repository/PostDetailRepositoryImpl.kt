package com.ich.reciptopia.data.repository

import com.ich.reciptopia.data.remote.ReciptopiaApi
import com.ich.reciptopia.domain.model.Post
import com.ich.reciptopia.domain.repository.PostDetailRepository

class PostDetailRepositoryImpl(
    private val api: ReciptopiaApi
): PostDetailRepository {
    private val testPosts = mutableListOf(
        Post(
            ownerId = 1L,
            title = "포스트1",
            content = "포스트 1의 내용",
            pictureUrls = listOf("https://i.imgur.com/JOKsNeT.jpg","https://avatars.githubusercontent.com/u/77564110?s=200&v=4"),
            views = 10L
        ).also { it.id = 1L },
        Post(
            ownerId = 1L,
            title = "포스트2",
            content = "포스트 2의 내용",
            pictureUrls = listOf("https://i.imgur.com/JOKsNeT.jpg","https://avatars.githubusercontent.com/u/77564110?s=200&v=4"),
            views = 15L
        ).also { it.id = 2L },
        Post(
            ownerId = 2L,
            title = "포스트3",
            content = "포스트 3의 내용",
            pictureUrls = listOf("https://i.imgur.com/JOKsNeT.jpg","https://i.imgur.com/JOKsNeT.jpg","https://i.imgur.com/JOKsNeT.jpg",
                "https://i.imgur.com/JOKsNeT.jpg","https://i.imgur.com/JOKsNeT.jpg","https://i.imgur.com/JOKsNeT.jpg"),
            views = 120L
        ).also { it.id = 3L }
    )

    override suspend fun getPost(postId: Long): Post {
        return testPosts.find { it.id == postId }!!
    }
}