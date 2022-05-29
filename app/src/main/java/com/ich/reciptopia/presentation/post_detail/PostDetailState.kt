package com.ich.reciptopia.presentation.post_detail

import com.ich.reciptopia.domain.model.*

data class PostDetailState(
    val isLoading: Boolean = false,
    val commentText: String = "",
    val curPost: Post? = null,
    val mainIngredients: List<MainIngredient> = emptyList(),
    val subIngredients: List<SubIngredient> = emptyList(),
    val currentUser: User? = null,
    val curRecipe: Recipe? = null,
    val curPostSteps: List<Step> = emptyList(),
    val comments: List<Comment> = emptyList()
)
