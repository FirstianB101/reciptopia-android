package com.ich.reciptopia.data.remote.dto

data class StepImagesDto(
    val stepImgs: LinkedHashMap<String, StepImageResponseDto>
)

fun StepImagesDto.toStepImageIdsList(): List<Long>{
    val list = mutableListOf<Long>()
    stepImgs.keys.forEach {
        list.add(stepImgs[it]?.id!!)
    }
    return list
}
