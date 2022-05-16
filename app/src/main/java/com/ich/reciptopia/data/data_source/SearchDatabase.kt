package com.ich.reciptopia.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ich.reciptopia.common.util.ChipInfoListTypeConverter
import com.ich.reciptopia.common.util.FavoritePostTypeConverter
import com.ich.reciptopia.domain.model.FavoriteEntity
import com.ich.reciptopia.domain.model.SearchHistoryEntity

@Database(
    entities = [SearchHistoryEntity::class, FavoriteEntity::class],
    version = 1
)
@TypeConverters(
    value = [
        ChipInfoListTypeConverter::class,
        FavoritePostTypeConverter::class
    ]
)
abstract class SearchDatabase: RoomDatabase() {
    abstract val searchDao: SearchDao

    companion object{
        const val DATABASE_NAME = "search_db"
    }
}