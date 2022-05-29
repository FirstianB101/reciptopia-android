package com.ich.reciptopia.data.remote.dto

import com.ich.reciptopia.domain.model.Post

data class PostWithCommentAndLikeTagCountsDto(
    val postMap: Map<String, PostDto>? = null,
    val commentCount: Int? = null,
    val likeTagCount: Int? = null
)

fun PostWithCommentAndLikeTagCountsDto.toPostList(): List<Post>{
    val posts = mutableListOf<Post>()
    postMap?.keys?.forEach {
        posts.add(
            postMap[it]!!.toPost().copy(
                likeCount = likeTagCount,
                commentCount = commentCount
            )
        )
    }
    return posts
}