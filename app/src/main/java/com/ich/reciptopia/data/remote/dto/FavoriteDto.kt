package com.ich.reciptopia.data.remote.dto

import com.ich.reciptopia.domain.model.Favorite

data class FavoriteDto(
    var id: Long? = null,
    var ownerId: Long? = null,
    var postId: Long? = null
)

fun FavoriteDto.toFavorite(): Favorite{
    return Favorite(
        id = id,
        ownerId = ownerId,
        postId = postId
    )
}