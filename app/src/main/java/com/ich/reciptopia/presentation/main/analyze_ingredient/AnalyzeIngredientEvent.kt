package com.ich.reciptopia.presentation.main.analyze_ingredient

import android.graphics.Bitmap

sealed class AnalyzeIngredientEvent{
    data class OnImageCaptured(val image: Bitmap): AnalyzeIngredientEvent()
    data class DeleteImage(val idx: Int): AnalyzeIngredientEvent()
    data class ManageDialogStateChanged(val show: Boolean): AnalyzeIngredientEvent()
    object CloseAnalyzeResultDialog: AnalyzeIngredientEvent()
    object StartAnalyzing: AnalyzeIngredientEvent()
}
