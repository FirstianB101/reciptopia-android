package com.ich.reciptopia.domain.model

data class Post(
    val id: Long? = null,
    val ownerId: Long? = null,
    val title: String? = null,
    val content: String? = null,
    val pictureUrls: List<String?> = emptyList(),
    val views: Long? = null,
    val owner: Account? = null,
    val favoriteNotLogin: Boolean = false,
    val favoriteLogin: Boolean = false
)