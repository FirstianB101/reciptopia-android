package com.ich.reciptopia.domain.use_case.search

import android.database.sqlite.SQLiteException
import com.ich.reciptopia.domain.model.FavoriteEntity
import com.ich.reciptopia.domain.repository.SearchRepository

class DeleteFavoriteEntity(
    private val repository: SearchRepository
) {

    suspend operator fun invoke(favoriteEntity: FavoriteEntity) {
        try{
            repository.deleteFavoriteEntity(favoriteEntity)
        }catch(e: SQLiteException){
            e.printStackTrace()
        }
    }
}