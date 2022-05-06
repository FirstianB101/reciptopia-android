package com.ich.reciptopia.data.remote.dto

import com.ich.reciptopia.domain.model.Post

data class PostDto(
    var ownerId: Long? = null,
    var title: String? = null,
    var content: String? = null,
    var pictureUrls: List<String?> = emptyList(),
    var views: Long? = null
){
    var id: Long? = null
}

fun PostDto.toPost(): Post{
    return Post(
        ownerId = ownerId,
        title = title,
        content = content,
        pictureUrls = pictureUrls,
        views = views
    ).also{ it.id = id }
}