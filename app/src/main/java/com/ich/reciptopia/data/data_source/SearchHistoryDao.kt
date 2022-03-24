package com.ich.reciptopia.data.data_source

import androidx.room.*
import com.ich.reciptopia.domain.model.SearchHistory
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchHistoryDao {

    @Query("SELECT * FROM searchhistory ORDER BY id DESC")
    fun getSearchHistories(): Flow<List<SearchHistory>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearchHistory(history: SearchHistory)

    @Delete
    suspend fun deleteSearchHistory(history: SearchHistory)
}