package com.ich.reciptopia.data.repository

import com.ich.reciptopia.data.remote.ReciptopiaApi
import com.ich.reciptopia.domain.model.Account
import com.ich.reciptopia.domain.model.Post
import com.ich.reciptopia.domain.model.PostLikeTag
import com.ich.reciptopia.domain.repository.CommunityRepository

class CommunityRepositoryImpl(
    private val api: ReciptopiaApi
): CommunityRepository {

    private var nextPostId = 4L
    private var nextTagId = 4L
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

    private val testPostLikeTags = mutableListOf(
        PostLikeTag(
            ownerId = 1L,
            postId = 1L
        ).also { it.id = 1L },
        PostLikeTag(
            ownerId = 1L,
            postId = 2L
        ).also { it.id = 1L },
        PostLikeTag(
            ownerId = 2L,
            postId = 3L
        ).also { it.id = 1L }
    )

    private val testOwner = mutableListOf(
        Account(nickname = "momomomo").also { it.id = 1L },
        Account(nickname = "balbalbal").also { it.id = 2L },
    )

    override suspend fun getOwnerOfPost(accountId: Long): Account {
        return testOwner.find{it.id == accountId}!!
    }

    override suspend fun postLike(postLikeTag: PostLikeTag): PostLikeTag {
        testPostLikeTags.add(postLikeTag.also{it.id = nextTagId++})
        return postLikeTag
    }

    override suspend fun getPostLikeTags(): List<PostLikeTag> {
        return testPostLikeTags
    }

    override suspend fun getPostsByTime(): List<Post> {
        return testPosts
    }

    override suspend fun getPostsByViews(): List<Post> {
        return testPosts.reversed()
    }

    override suspend fun createPost(post: Post): Post {
        testPosts.add(post.also{it.id = nextPostId++})
        return post
    }
}