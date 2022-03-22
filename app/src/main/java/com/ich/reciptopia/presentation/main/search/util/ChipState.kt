package com.ich.reciptopia.presentation.main.search.util

import androidx.compose.runtime.MutableState

data class ChipState(
    var text: String,
    var isSubIngredient: MutableState<Boolean>
)