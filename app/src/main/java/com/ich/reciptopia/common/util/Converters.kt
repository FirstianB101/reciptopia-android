package com.ich.reciptopia.common.util

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.ich.reciptopia.domain.model.Account
import com.ich.reciptopia.domain.model.Post
import com.ich.reciptopia.presentation.main.search.util.ChipInfo

@ProvidedTypeConverter
class ChipInfoListTypeConverter(private val gson: Gson) {

    @TypeConverter
    fun listToJson(value: List<ChipInfo>): String? {
        return gson.toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String): List<ChipInfo>{
        return gson.fromJson(value, Array<ChipInfo>::class.java).toList()
    }
}

@ProvidedTypeConverter
class FavoritePostTypeConverter(private val gson: Gson){

    @TypeConverter
    fun postToJson(value: Post): String? {
        return gson.toJson(value)
    }

    @TypeConverter
    fun jsonToPost(value: String): Post{
        return gson.fromJson(value, Post::class.java)
    }
}