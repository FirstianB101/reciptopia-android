package com.ich.reciptopia.domain.model

data class PostWithCommentAndLikeTagCounts(
    val postMap: Map<Long, Post>? = null,
    val commentCount: Int? = null,
    val likeTagCount: Int? = null
)

fun PostWithCommentAndLikeTagCounts.toPostList(): List<Post>{
    val list = mutableListOf<Post>()
    postMap?.keys?.forEach {
        list.add(postMap[it]!!)
    }
    return list
}