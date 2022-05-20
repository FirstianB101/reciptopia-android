package com.ich.reciptopia.presentation.post_detail

import com.ich.reciptopia.domain.model.MainIngredient
import com.ich.reciptopia.domain.model.Post
import com.ich.reciptopia.domain.model.SubIngredient
import com.ich.reciptopia.domain.model.User

data class PostDetailState(
    val isLoading: Boolean = false,
    val commentText: String = "",
    val curPost: Post? = null,
    val mainIngredients: List<MainIngredient> = listOf(MainIngredient(null,null,"메인재료","20g")),
    val subIngredients: List<SubIngredient> = listOf(SubIngredient(null,null,"서브재료","50g")),
    val currentUser: User? = null
)
