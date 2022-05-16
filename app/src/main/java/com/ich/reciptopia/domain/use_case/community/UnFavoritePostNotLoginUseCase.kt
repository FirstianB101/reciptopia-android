package com.ich.reciptopia.domain.use_case.community

import android.database.sqlite.SQLiteException
import com.ich.reciptopia.domain.model.Post
import com.ich.reciptopia.domain.repository.CommunityRepository
import javax.inject.Inject

class UnFavoritePostNotLoginUseCase  @Inject constructor(
    private val repository: CommunityRepository
){
    suspend operator fun invoke(post: Post){
        try{
            repository.unFavoritePostNotLogin(post)
        }catch(e: SQLiteException){
            e.printStackTrace()
        }
    }
}