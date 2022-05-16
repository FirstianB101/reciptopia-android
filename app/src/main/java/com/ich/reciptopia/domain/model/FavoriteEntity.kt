package com.ich.reciptopia.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val post: Post
)