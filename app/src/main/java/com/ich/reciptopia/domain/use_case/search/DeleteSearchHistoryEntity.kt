package com.ich.reciptopia.domain.use_case.search

import android.database.sqlite.SQLiteException
import com.ich.reciptopia.domain.model.SearchHistoryEntity
import com.ich.reciptopia.domain.repository.SearchRepository

class DeleteSearchHistoryEntity(
    private val repository: SearchRepository
) {
    suspend operator fun invoke(historyEntity: SearchHistoryEntity) {
        try{
            repository.deleteSearchHistoryEntity(historyEntity)
        }catch(e: SQLiteException){
            e.printStackTrace()
        }
    }
}