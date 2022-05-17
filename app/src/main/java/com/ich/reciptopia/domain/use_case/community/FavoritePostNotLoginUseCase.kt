package com.ich.reciptopia.domain.use_case.community

import android.database.sqlite.SQLiteException
import com.ich.reciptopia.domain.repository.CommunityRepository
import javax.inject.Inject

class FavoritePostNotLoginUseCase @Inject constructor(
    private val repository: CommunityRepository
){
    suspend operator fun invoke(postId: Long){
        try{
            repository.favoritePostNotLogin(postId)
        }catch(e: SQLiteException){
            e.printStackTrace()
        }
    }
}