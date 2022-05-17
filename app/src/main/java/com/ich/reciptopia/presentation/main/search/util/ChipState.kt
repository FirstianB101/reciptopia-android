package com.ich.reciptopia.presentation.main.search.util

import androidx.compose.runtime.MutableState

data class ChipState(
    var text: String,
    var isSubIngredient: MutableState<Boolean>
){
    fun toChipInfo(): ChipInfo{
        return ChipInfo(text,isSubIngredient.value)
    }
}

data class ChipInfo(
    var text: String,
    var isSubIngredient: Boolean
)

fun List<ChipInfo>.getTextsWithComma(): String {
    return StringBuilder().also { sb ->
        for(info in this){
            sb.append("${info.text}, ")
        }
        if(this.isNotEmpty())
            sb.delete(sb.length - 2, sb.length)
    }.toString()
}

fun List<String?>.linkStringsWithComma(): String{
    return StringBuilder().also { sb ->
        for(name in this){
            sb.append("${name}, ")
        }
        if(this.isNotEmpty())
            sb.delete(sb.length - 2, sb.length)
    }.toString()
}