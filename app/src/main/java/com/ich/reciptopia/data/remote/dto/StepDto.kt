package com.ich.reciptopia.data.remote.dto

import com.ich.reciptopia.domain.model.Step

data class StepDto(
    var recipeId: Long,
    var description: String? = null,
    var pictureUrl: String? = null
){
    var id: Long? = null
}

fun StepDto.toStep(): Step{
    return Step(
        recipeId = recipeId,
        description = description,
        pictureUrl = pictureUrl
    ).also { it.id = id }
}
