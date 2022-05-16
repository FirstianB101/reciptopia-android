package com.ich.reciptopia.data.remote.dto

import com.ich.reciptopia.domain.model.Post

data class PostDto(
    var id: Long? = null,
    var ownerId: Long? = null,
    var title: String? = null,
    var content: String? = null,
    var pictureUrls: List<String?> = emptyList(),
    var views: Long? = null
)

fun PostDto.toPost(): Post{
    return Post(
        id = id,
        ownerId = ownerId,
        title = title,
        content = content,
        pictureUrls = pictureUrls,
        views = views
    )
}