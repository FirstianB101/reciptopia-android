package com.ich.reciptopia.data.remote.dto

import com.ich.reciptopia.domain.model.PostLikeTag

data class PostLikeTagsDto(
    val postLikeTags: LinkedHashMap<String, List<PostLikeTagDto>>
)

fun PostLikeTagsDto.toPostLikeTagList(): List<PostLikeTag>{
    val list = mutableListOf<PostLikeTag>()
    postLikeTags.keys.forEach{ key ->
        list.addAll(postLikeTags[key]!!.map{it.toPostLikeTag()})
    }
    return list
}