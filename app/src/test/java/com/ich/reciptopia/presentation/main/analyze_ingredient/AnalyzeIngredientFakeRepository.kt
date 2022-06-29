package com.ich.reciptopia.presentation.main.analyze_ingredient

import android.graphics.Bitmap
import com.ich.reciptopia.domain.model.AnalyzeResult
import com.ich.reciptopia.domain.model.ImageResponse
import com.ich.reciptopia.domain.repository.AnalyzeIngredientRepository

class AnalyzeIngredientFakeRepository: AnalyzeIngredientRepository {
    val successResponse = ImageResponse(
        response_data = AnalyzeResult(
            predicts = mapOf(
                "1" to "ingredient1",
                "2" to "ingredient2",
                "3" to "ingredient3"
            )
        ),
        status = "success"
    )

    override suspend fun getAnalyzeResult(images: List<Bitmap>): ImageResponse {
        return successResponse
    }
}