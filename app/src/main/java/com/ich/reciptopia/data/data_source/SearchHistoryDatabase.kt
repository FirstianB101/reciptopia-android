package com.ich.reciptopia.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ich.reciptopia.common.util.ChipInfoListTypeConverter
import com.ich.reciptopia.domain.model.SearchHistoryEntity

@Database(
    entities = [SearchHistoryEntity::class],
    version = 1
)
@TypeConverters(
    value = [
        ChipInfoListTypeConverter::class
    ]
)
abstract class SearchHistoryDatabase: RoomDatabase() {
    abstract val searchHistoryDao: SearchHistoryDao

    companion object{
        const val DATABASE_NAME = "search_history_db"
    }
}