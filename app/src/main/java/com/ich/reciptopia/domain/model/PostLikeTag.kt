package com.ich.reciptopia.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PostLikeTag(
    @PrimaryKey val id: Long? = 0,
    val ownerId: Long? = null,
    val postId: Long? = null
)