package com.ich.reciptopia.domain.model

data class Favorite(
    var ownerId: Long? = null,
    var postId: Long? = null
){
    var id: Long? = null
}
