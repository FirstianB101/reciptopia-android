package com.ich.reciptopia.data.repository

import com.ich.reciptopia.domain.model.*

//https://i.ibb.co/KrSf4DL/burger.jpg
//https://i.ibb.co/B6STW37/gimbap.jpg
//https://i.ibb.co/g6CSWWP/gimbap2.jpg
//https://i.ibb.co/9qGmP2H/gimbap3.jpg
//https://i.ibb.co/hL7v37N/kimchijjigae.jpg
//https://i.ibb.co/CWqtyNt/naengmyeon.jpg

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
    var nextRecipeId = 3L
    var nextCommentId = 5L
    var nextReplyId = 4L
    var nextStepId = 4L

    val testPosts = mutableListOf(
        Post(
            id = 1L,
            ownerId = 1L,
            title = "기가막힌 햄버거",
            content = "고기, 상추, 양파, 소스가 들어간 햄버거",
            pictureUrls = listOf("https://i.ibb.co/KrSf4DL/burger.jpg"),
            views = 10L,
            likeCount = 5,
            commentCount = 4
        ),
        Post(
            id = 2L,
            ownerId = 1L,
            title = "눈물나는 김밥",
            content = "피크닉의 필수 음식 김밥",
            pictureUrls = listOf("https://i.ibb.co/B6STW37/gimbap.jpg","https://i.ibb.co/g6CSWWP/gimbap2.jpg","https://i.ibb.co/9qGmP2H/gimbap3.jpg"),
            views = 15L,
            likeCount = 50,
            commentCount = 40
        ),
        Post(
            id = 3L,
            ownerId = 2L,
            title = "한국의 매운맛 김치찌개",
            content = "얼큰하고 구수한 김치찌개",
            pictureUrls = listOf("https://i.ibb.co/hL7v37N/kimchijjigae.jpg"),
            views = 120L,
            likeCount = 500,
            commentCount = 0
        ),
        Post(
            id = 4L,
            ownerId = 1L,
            title = "시원한 냉면",
            content = "여름엔 역시 냉면",
            pictureUrls = listOf("https://i.ibb.co/CWqtyNt/naengmyeon.jpg"),
            views = 30L,
            likeCount = 5000,
            commentCount = 0
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
            nickname = "테스트 유저 1"
        ),
        Account(
            id = 2L,
            nickname = "테스트 유저 2"
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

    val testRecipes = mutableListOf(
        Recipe(id = 1L, postId = 1L),
        Recipe(id = 2L, postId = 2L),
        Recipe(id = 3L, postId = 3L),
        Recipe(id = 4L, postId = 4L),
    )

    val testComments = mutableListOf(
        Comment(
            id = 1L,
            ownerId = 1L,
            postId = 1L,
            content = "첫 번째 댓글"
        ),
        Comment(
            id = 2L,
            ownerId = 2L,
            postId = 1L,
            content = "맛있어 보이네요"
        ),
        Comment(
            id = 3L,
            ownerId = 1L,
            postId = 2L,
            content = "이것이 댓글이다"
        ),
        Comment(
            id = 4L,
            ownerId = 2L,
            postId = 2L,
            content = "꼴랑 이게 댓글?"
        )
    )
    
    val testReplies = mutableListOf(
        Reply(
            id = 1L,
            ownerId = 2L,
            commentId = 1L,
            content = "첫 번째 답글"
        ),
        Reply(
            id = 2L,
            ownerId = 1L,
            commentId = 4L,
            content = "꼴랑 이게 답글?"
        ),
        Reply(
            id = 3L,
            ownerId = 1L,
            commentId = 4L,
            content = "꼴랑 답글에 답글"
        )
    )

    val testSteps = mutableListOf(
        Step(
            id = 1L,
            recipeId = 1L,
            description = "물을 500mL 넣고 끓인다",
            pictureUrl = null
        ),
        Step(
            id = 2L,
            recipeId = 1L,
            description = "완성한다",
            pictureUrl = null
        ),
        Step(
            id = 3L,
            recipeId = 2L,
            description = "사 온다",
            pictureUrl = null
        )
    )
    
    val testMainIngredients = mutableListOf(
        MainIngredient(
            id = 1L,
            recipeId = 1L,
            name = "고추장",
            detail = "1큰술"
        ),
        MainIngredient(
            id = 2L,
            recipeId = 1L,
            name = "밥",
            detail = "1공기"
        ),
        MainIngredient(
            id = 3L,
            recipeId = 2L,
            name = "포도",
            detail = "1송이"
        ),
        MainIngredient(
            id = 4L,
            recipeId = 2L,
            name = "사과",
            detail = "1개"
        ),
    )

    val testSubIngredients = mutableListOf(
        SubIngredient(
            id = 1L,
            recipeId = 1L,
            name = "간장",
            detail = "1작은술"
        ),
        SubIngredient(
            id = 2L,
            recipeId = 1L,
            name = "초콜릿",
            detail = "1개"
        ),
        SubIngredient(
            id = 3L,
            recipeId = 2L,
            name = "양파",
            detail = "1개"
        ),
        SubIngredient(
            id = 4L,
            recipeId = 2L,
            name = "배추김치",
            detail = "100g"
        ),
    )
}