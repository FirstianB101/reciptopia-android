package com.ich.reciptopia.presentation.main.analyze_ingredient

import android.graphics.Bitmap

data class AnalyzeIngredientState(
    val images: List<Bitmap> = emptyList(),
    val showManageDialog: Boolean = false,
    val showAnalyzeResultDialog: Boolean = false
)