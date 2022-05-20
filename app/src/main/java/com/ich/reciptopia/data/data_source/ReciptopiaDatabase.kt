package com.ich.reciptopia.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ich.reciptopia.common.util.FavoriteTypeConverter
import com.ich.reciptopia.common.util.SearchHistoryTypeConverter
import com.ich.reciptopia.domain.model.Favorite
import com.ich.reciptopia.domain.model.SearchHistory

@Database(
    entities = [SearchHistory::class, Favorite::class],
    version = 1
)
@TypeConverters(
    value = [
        SearchHistoryTypeConverter::class,
        FavoriteTypeConverter::class
    ]
)
abstract class ReciptopiaDatabase: RoomDatabase() {
    abstract val reciptopiaDao: ReciptopiaDao

    companion object{
        const val DATABASE_NAME = "search_db"
    }
}