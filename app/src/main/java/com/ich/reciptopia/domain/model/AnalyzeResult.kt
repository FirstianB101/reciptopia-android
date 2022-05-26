package com.ich.reciptopia.domain.model

data class AnalyzeResult(
    val predicts: Map<String, String>? = null,
    val message: String? = null
)