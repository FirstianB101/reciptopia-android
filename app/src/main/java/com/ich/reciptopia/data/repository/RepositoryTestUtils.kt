package com.ich.reciptopia.data.repository

import com.ich.reciptopia.domain.model.*

object RepositoryTestUtils {

    // Post id 1~3
    // owner id 1~2
    // favorite id 1~2
    // like tag id 1~3
    // history id 1~4
    var nextPostId = 4L
    var nextOwnerId = 3L
    var nextFavoriteId = 3L
    var nextLikeTagId = 4L
    var nextHistoryId = 5L

    val testPosts = mutableListOf(
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

    val testPostLikeTags = mutableListOf(
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
            id = 3L,
            ownerId = 2L,
            postId = 3L
        )
    )

    val testOwner = mutableListOf(
        Account(
            id = 1L,
            nickname = "momomomo"
        ),
        Account(
            id = 2L,
            nickname = "balbalbal"
        ),
    )

    val testFavorites = mutableListOf(
        Favorite(
            id = 1L,
            ownerId = 1L,
            postId = 1L
        ),
        Favorite(
            id = 2L,
            ownerId = 1L,
            postId = 2L
        )
    )

    val testHistories = mutableListOf(
        SearchHistory(
            id = 1L,
            ownerId = 1L,
            listOf("사과", "양파", "고추장")
        ),
        SearchHistory(
            id = 2L,
            ownerId = 1L,
            listOf("감자", "고구마")
        ),
        SearchHistory(
            id = 3L,
            ownerId = 1L,
            listOf("파", "양파", "간장")
        ),
        SearchHistory(
            id = 4L,
            ownerId = 2L,
            listOf("배추김치", "쌀밥")
        )
    )
}