package com.ich.reciptopia.data.remote.dto

import com.ich.reciptopia.domain.model.AnalyzeResult

data class AnalyzeResultDto(
    val predicts: Map<String, String>? = null,
    val message: String? = null
)

fun AnalyzeResultDto.toAnalyzeResult(): AnalyzeResult{
    return AnalyzeResult(
        predicts = predicts,
        message = message
    )
}