package com.ich.reciptopia.data.remote.dto

import com.ich.reciptopia.domain.model.Favorite

data class FavoriteDto(
    var ownerId: Long? = null,
    var postId: Long? = null
){
    var id: Long? = null
}

fun FavoriteDto.toFavorite(): Favorite{
    return Favorite(
        ownerId = ownerId,
        postId = postId
    ).also { it.id = id }
}