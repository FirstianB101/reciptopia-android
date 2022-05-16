package com.ich.reciptopia.data.remote.dto

import com.ich.reciptopia.domain.model.PostLikeTag

data class PostLikeTagDto(
    var id: Long? = null,
    var ownerId: Long? = null,
    var postId: Long? = null
)

fun PostLikeTagDto.toPostLikeTag(): PostLikeTag{
    return PostLikeTag(
        id = id,
        ownerId = ownerId,
        postId = postId
    )
}