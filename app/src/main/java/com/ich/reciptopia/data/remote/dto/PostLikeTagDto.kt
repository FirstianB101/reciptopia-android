package com.ich.reciptopia.data.remote.dto

import com.ich.reciptopia.domain.model.PostLikeTag

data class PostLikeTagDto(
    var ownerId: Long? = null,
    var postId: Long? = null
){
    var id: Long? = null
}

fun PostLikeTagDto.toPostLikeTag(): PostLikeTag{
    return PostLikeTag(
        ownerId = ownerId,
        postId = postId
    ).also{ it.id = id }
}