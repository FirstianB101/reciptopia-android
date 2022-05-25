package com.ich.reciptopia.data.remote.dto

import com.ich.reciptopia.domain.model.Post
import com.ich.reciptopia.domain.model.PostWithCommentAndLikeTagCounts

data class PostWithCommentAndLikeTagCountsDto(
    val postMap: Map<String, PostDto>? = null,
    val commentCount: Int? = null,
    val likeTagCount: Int? = null
)

fun PostWithCommentAndLikeTagCountsDto.toPostWithCommentAndLikeTagCount(): PostWithCommentAndLikeTagCounts{
    val map = mutableMapOf<Long, Post>()
    postMap?.keys?.forEach {
        map[it.toLong()] = postMap[it]!!.toPost()
    }
    return PostWithCommentAndLikeTagCounts(
        postMap = map,
        commentCount = commentCount,
        likeTagCount = likeTagCount
    )
}

fun PostWithCommentAndLikeTagCountsDto.toPostList(): List<Post>{
    val posts = mutableListOf<Post>()
    postMap?.keys?.forEach {
        posts.add(postMap[it]!!.toPost())
    }
    return posts
}