package com.ich.reciptopia.domain.model

data class Reply(
    var ownerId: Long? = null,
    var commentId: Long? = null,
    var content: String? = null
){
    var id: Long? = null
}