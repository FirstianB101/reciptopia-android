package com.ich.reciptopia.presentation.main.search.util

fun List<ChipState>.addChipState(
    add: (MutableList<ChipState>) -> Unit
): List<ChipState>{
    return this.toMutableList().also(add)
}

fun List<ChipState>.removeChipState(
    idx: Int
): List<ChipState>{
    return this.toMutableList().also { it.removeAt(idx) }
}