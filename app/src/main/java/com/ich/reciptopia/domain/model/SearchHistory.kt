package com.ich.reciptopia.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ich.reciptopia.presentation.main.search.util.ChipInfo

@Entity
data class SearchHistory(
    val ingredients: List<ChipInfo>,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
){
    override fun toString(): String {
        return StringBuilder().also { sb ->
            for(ingredient in ingredients){
                sb.append("${ingredient.text}, ")
            }
            if(ingredients.isNotEmpty())
                sb.delete(sb.length - 2, sb.length)
        }.toString()
    }
}
