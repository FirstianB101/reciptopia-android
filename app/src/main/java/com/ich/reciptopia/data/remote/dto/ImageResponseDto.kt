package com.ich.reciptopia.data.remote.dto

import com.ich.reciptopia.domain.model.ImageResponse

data class ImageResponseDto(
    val status: String? = null,
    val response_data: AnalyzeResultDto? = null
)

fun ImageResponseDto.toImageResponseBody(): ImageResponse{
    return ImageResponse(
        status = status,
        response_data = response_data?.toAnalyzeResult()
    )
}