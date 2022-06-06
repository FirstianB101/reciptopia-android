package com.ich.reciptopia.data.remote.dto

import com.ich.reciptopia.domain.model.Step

data class StepsDto(
    val steps: LinkedHashMap<String, StepDto>
)

fun StepsDto.toStepList(): List<Step>{
    val list = mutableListOf<Step>()
    steps.keys.forEach {
        list.add(steps[it]!!.toStep())
    }
    return list
}
