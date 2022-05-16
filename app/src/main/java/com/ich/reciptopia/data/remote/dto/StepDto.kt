package com.ich.reciptopia.data.remote.dto

import com.ich.reciptopia.domain.model.Step

data class StepDto(
    var id: Long? = null,
    var recipeId: Long,
    var description: String? = null,
    var pictureUrl: String? = null
)

fun StepDto.toStep(): Step{
    return Step(
        id = id,
        recipeId = recipeId,
        description = description,
        pictureUrl = pictureUrl
    )
}
