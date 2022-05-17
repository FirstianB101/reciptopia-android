package com.ich.reciptopia.common.util

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.ich.reciptopia.domain.model.Post

@ProvidedTypeConverter
class SearchHistoryTypeConverter(private val gson: Gson) {

    @TypeConverter
    fun stringListToJson(value: List<String>): String? {
        return gson.toJson(value)
    }

    @TypeConverter
    fun jsonToStringList(value: String): List<String>{
        return gson.fromJson(value, Array<String>::class.java).toList()
    }
}

@ProvidedTypeConverter
class FavoriteTypeConverter(private val gson: Gson){

    @TypeConverter
    fun postToEmptyJson(value: Post?): String? {
        return null
    }

    @TypeConverter
    fun jsonToEmptyPost(value: String?): Post? {
        return null
    }
}