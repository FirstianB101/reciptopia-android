package com.ich.reciptopia.data.remote.dto

data class PostImagesDto(
    val postImgs: LinkedHashMap<String, PostImageResponseDto>
)

fun PostImagesDto.toPostImageIdsList(): List<Long>{
    val list = mutableListOf<Long>()
    postImgs.keys.forEach {
        list.add(postImgs[it]?.id!!)
    }
    return list
}
