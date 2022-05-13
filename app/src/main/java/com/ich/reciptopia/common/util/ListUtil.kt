package com.ich.reciptopia.common.util

import android.graphics.Bitmap
import com.ich.reciptopia.presentation.main.search.util.ChipState

fun List<Bitmap>.getAddedList(image: Bitmap): List<Bitmap>{
    return this.toMutableList().also{
        it.add(image)
    }
}

fun List<Bitmap>.getRemovedList(images: List<Bitmap>): List<Bitmap>{
    return this.toMutableList().also{
        images.forEach { image ->
            it.remove(image)
        }
    }
}

fun List<ChipState>.getAddedList(chipState: ChipState): List<ChipState>{
    return this.toMutableList().also{
        it.add(chipState)
    }
}

fun List<ChipState>.getRemovedList(chipState: ChipState): List<ChipState>{
    return this.toMutableList().also{
        it.remove(chipState)
    }
}

fun List<String>.getAddedList(uri: String): List<String>{
    return this.toMutableList().also{
        it.add(uri)
    }
}

fun List<String>.getRemovedList(idx: Int): List<String>{
    return this.toMutableList().also{
        it.removeAt(idx)
    }
}