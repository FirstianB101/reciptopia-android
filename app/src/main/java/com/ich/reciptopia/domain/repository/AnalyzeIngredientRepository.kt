package com.ich.reciptopia.domain.repository

import android.graphics.Bitmap
import com.ich.reciptopia.domain.model.ImageResponse

interface AnalyzeIngredientRepository {
    suspend fun getAnalyzeResult(images: List<Bitmap>): ImageResponse
}