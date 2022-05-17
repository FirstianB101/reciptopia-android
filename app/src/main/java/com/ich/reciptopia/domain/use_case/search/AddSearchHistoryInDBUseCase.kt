package com.ich.reciptopia.domain.use_case.search

import android.database.sqlite.SQLiteException
import com.ich.reciptopia.domain.model.SearchHistory
import com.ich.reciptopia.domain.repository.SearchRepository

class AddSearchHistoryInDBUseCase(
    private val repository: SearchRepository
) {
    suspend operator fun invoke(history: SearchHistory) {
        try{
            repository.insertSearchHistoryInDB(history)
        }catch(e: SQLiteException){
            e.printStackTrace()
        }
    }
}