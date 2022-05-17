package com.ich.reciptopia.data.repository

import com.ich.reciptopia.data.data_source.SearchDao
import com.ich.reciptopia.data.remote.ReciptopiaApi
import com.ich.reciptopia.domain.model.Account
import com.ich.reciptopia.domain.model.FavoriteEntity
import com.ich.reciptopia.domain.model.Post
import com.ich.reciptopia.domain.repository.PostDetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class PostDetailRepositoryImpl(
    private val api: ReciptopiaApi,
    private val dao: SearchDao
): PostDetailRepository {
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

    override suspend fun getPost(postId: Long): Post {
        return testPosts.find { it.id == postId }!!
    }

    override suspend fun getOwnerOfPost(accountId: Long): Account {
        return testOwner.find { it.id == accountId }!!
    }

    override suspend fun favoritePostNotLogin(post: Post) {
        dao.insertFavorite(FavoriteEntity(post = post))
    }

    override suspend fun unFavoritePostNotLogin(post: Post) {
        val entities = dao.getFavorites().first()
        val entity = entities.find{ it.post.id == post.id }
        if (entity != null) {
            dao.deleteFavorite(entity)
        }
    }

    override suspend fun getFavoriteEntities(): Flow<List<FavoriteEntity>> {
        return dao.getFavorites()
    }
}