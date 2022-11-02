package com.ich.reciptopia.data.remote.dto

import com.ich.reciptopia.domain.model.StepImageResponse

data class StepImageResponseDto(
    val id :Long? = null,
    val uploadFileName: String? = null,
    val storeFile: String? = null,
    val stepId: Long? = null
)

fun StepImageResponseDto.toStepImageResponse(): StepImageResponse {
    return StepImageResponse(
        id = id,
        uploadFileName = uploadFileName,
        storeFile = storeFile,
        stepId = stepId
    )
}