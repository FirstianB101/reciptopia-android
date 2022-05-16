package com.ich.reciptopia.data.repository

import com.ich.reciptopia.data.data_source.SearchDao
import com.ich.reciptopia.data.remote.ReciptopiaApi
import com.ich.reciptopia.domain.model.Account
import com.ich.reciptopia.domain.model.FavoriteEntity
import com.ich.reciptopia.domain.model.Post
import com.ich.reciptopia.domain.model.PostLikeTag
import com.ich.reciptopia.domain.repository.CommunityRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class CommunityRepositoryImpl(
    private val api: ReciptopiaApi,
    private val dao: SearchDao
): CommunityRepository {

    private var nextPostId = 4L
    private var nextTagId = 4L
    private val testPosts = mutableListOf(
        Post(
            id = 1L,
            ownerId = 1L,
            title = "포스트1",
            content = "포스트 1의 내용",
            pictureUrls = listOf("https://i.imgur.com/JOKsNeT.jpg","https://avatars.githubusercontent.com/u/77564110?s=200&v=4"),
            views = 10L
        ),
        Post(
            id = 2L,
            ownerId = 1L,
            title = "포스트2",
            content = "포스트 2의 내용",
            pictureUrls = listOf("https://i.imgur.com/JOKsNeT.jpg","https://avatars.githubusercontent.com/u/77564110?s=200&v=4"),
            views = 15L
        ),
        Post(
            id = 3L,
            ownerId = 2L,
            title = "포스트3",
            content = "포스트 3의 내용",
            pictureUrls = listOf("https://i.imgur.com/JOKsNeT.jpg","https://i.imgur.com/JOKsNeT.jpg","https://i.imgur.com/JOKsNeT.jpg",
                "https://i.imgur.com/JOKsNeT.jpg","https://i.imgur.com/JOKsNeT.jpg","https://i.imgur.com/JOKsNeT.jpg"),
            views = 120L
        )
    )

    private val testPostLikeTags = mutableListOf(
        PostLikeTag(
            id = 1L,
            ownerId = 1L,
            postId = 1L
        ),
        PostLikeTag(
            id = 2L,
            ownerId = 1L,
            postId = 2L
        ),
        PostLikeTag(
            id = 1L,
            ownerId = 2L,
            postId = 3L
        )
    )

    private val testOwner = mutableListOf(
        Account(
            id = 1L,
            nickname = "momomomo"
        ),
        Account(
            id = 2L,
            nickname = "balbalbal"
        ),
    )

    override suspend fun getOwnerOfPost(accountId: Long): Account {
        return testOwner.find{it.id == accountId}!!
    }

    override suspend fun postLike(postLikeTag: PostLikeTag): PostLikeTag {
        testPostLikeTags.add(
            postLikeTag.copy(
                id = nextTagId++
            )
        )
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
        testPosts.add(
            post.copy(
                id = nextPostId++
            )
        )
        return post
    }

    override suspend fun favoritePostNotLogin(post: Post) {
        dao.insertFavorite(FavoriteEntity(post = post))
    }

    override suspend fun unFavoritePostNotLogin(post: Post) {
        val entities = dao.getFavorites().first()
        val entity = entities.find{ it.post.id == post.id}
        if (entity != null) {
            dao.deleteFavorite(entity)
        }
    }

    override suspend fun getFavoriteEntities(): Flow<List<FavoriteEntity>> {
        return dao.getFavorites()
    }
}