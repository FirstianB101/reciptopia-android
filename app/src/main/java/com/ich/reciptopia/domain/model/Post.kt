package com.ich.reciptopia.domain.model

data class Post(
    var ownerId: Long? = null,
    var title: String? = null,
    var content: String? = null,
    var pictureUrls: List<String?> = emptyList(),
    var views: Long? = null,
    var owner: Account? = null
){
    var id: Long? = null
}
