package com.ich.reciptopia.domain.model

data class Post(
    val id: Long? = null,
    val ownerId: Long? = null,
    val title: String? = null,
    val content: String? = null,
    val pictureUrls: List<String?> = emptyList(),
    val views: Long? = null,
    val owner: Account? = null,
    val isFavorite: Boolean = false,
    val like: Boolean = false,
    val commentCount: Int? = null,
    val likeCount: Int? = null
)