package com.ich.reciptopia.domain.use_case.search

import android.database.sqlite.SQLiteException
import com.ich.reciptopia.domain.model.Favorite
import com.ich.reciptopia.domain.repository.SearchRepository

class DeleteFavoriteFromDBUseCase(
    private val repository: SearchRepository
) {
    suspend operator fun invoke(favorite: Favorite) {
        try{
            repository.deleteFavoriteFromDB(favorite)
        }catch(e: SQLiteException){
            e.printStackTrace()
        }
    }
}