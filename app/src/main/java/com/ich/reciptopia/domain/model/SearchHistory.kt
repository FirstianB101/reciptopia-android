package com.ich.reciptopia.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SearchHistory(
    @PrimaryKey val id: Long? = null,
    val ownerId: Long? = null,
    val ingredientNames: List<String?> = emptyList()
)
