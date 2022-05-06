package com.ich.reciptopia.domain.model

data class SubIngredient(
    var recipeId: Long? = null,
    var name: String? = null,
    var detail: String? = null
){
    var id: Long? = null
}
