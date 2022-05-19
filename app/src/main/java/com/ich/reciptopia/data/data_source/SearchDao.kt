package com.ich.reciptopia.data.data_source

import androidx.room.*
import com.ich.reciptopia.domain.model.Favorite
import com.ich.reciptopia.domain.model.PostLikeTag
import com.ich.reciptopia.domain.model.SearchHistory
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchDao {

    @Query("SELECT * FROM searchhistory ORDER BY id DESC")
    fun getSearchHistories(): Flow<List<SearchHistory>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearchHistory(historyEntity: SearchHistory)

    @Delete
    suspend fun deleteSearchHistory(historyEntity: SearchHistory)

    @Query("DELETE FROM searchhistory WHERE id = :historyId")
    suspend fun deleteSearchHistory(historyId: Long)


    @Query("SELECT * FROM favorite ORDER BY id DESC")
    fun getFavorites(): Flow<List<Favorite>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favoriteEntity: Favorite)

    @Delete
    suspend fun deleteFavorite(favoriteEntity: Favorite)

    @Query("DELETE FROM favorite WHERE postId = :postId")
    suspend fun deleteFavorite(postId: Long)
}