package com.ich.reciptopia.domain.model

data class Comment(
    var ownerId: Long? = null,
    var postId: Long? = null,
    var content: String? = null
){
    var id: Long? = null
}
