package com.ich.reciptopia.presentation.post_detail

import com.ich.reciptopia.domain.model.*

data class PostDetailState(
    val isLoading: Boolean = false,
    val curPost: Post? = null,
    val mainIngredients: List<MainIngredient> = emptyList(),
    val subIngredients: List<SubIngredient> = emptyList(),
    val currentUser: User? = null,
    val curRecipe: Recipe? = null,
    val curPostLikeTag: PostLikeTag? = null,
    val curPostFavorite: Favorite? = null,
    val curPostSteps: List<Step> = emptyList(),
    val isOwner: Boolean = false,
    val showSettingMenu: Boolean = false
)
