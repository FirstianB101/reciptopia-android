package com.ich.reciptopia.data.remote.dto

import com.ich.reciptopia.domain.model.Post

data class PostWithCommentAndLikeTagCountsDto(
    val postWithCommentAndLikeTagCounts: Map<String, PostWithCommentAndLikeTagCounts>
)

data class PostWithCommentAndLikeTagCounts(
    val post: PostDto? = null,
    val commentCount: Int? = null,
    val likeTagCount: Int? = null
)

fun PostWithCommentAndLikeTagCountsDto.toPostList(): List<Post>{
    val posts = mutableListOf<Post>()
    postWithCommentAndLikeTagCounts.keys.forEach {
        val post = postWithCommentAndLikeTagCounts[it]?.post?.toPost()!!
        val commentCount = postWithCommentAndLikeTagCounts[it]?.commentCount
        val likeCount = postWithCommentAndLikeTagCounts[it]?.likeTagCount
        posts.add(
            post.copy(
                commentCount = commentCount,
                likeCount = likeCount
            )
        )
    }
    return posts
}