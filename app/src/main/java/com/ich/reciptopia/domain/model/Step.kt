package com.ich.reciptopia.domain.model

data class Step(
    var recipeId: Long,
    var description: String? = null,
    var pictureUrl: String? = null
){
    var id: Long? = null
}
