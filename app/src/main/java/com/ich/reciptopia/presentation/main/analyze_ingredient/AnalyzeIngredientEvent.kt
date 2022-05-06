package com.ich.reciptopia.presentation.main.analyze_ingredient

import android.graphics.Bitmap

sealed class AnalyzeIngredientEvent{
    data class OnImageCaptured(val image: Bitmap): AnalyzeIngredientEvent()
    data class DeleteImages(val images: List<Bitmap>): AnalyzeIngredientEvent()
    data class ManageDialogStateChanged(val show: Boolean): AnalyzeIngredientEvent()
    data class ResultDialogStateChanged(val show: Boolean): AnalyzeIngredientEvent()
}
