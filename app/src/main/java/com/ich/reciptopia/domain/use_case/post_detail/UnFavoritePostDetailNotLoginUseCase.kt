package com.ich.reciptopia.domain.use_case.post_detail

import android.database.sqlite.SQLiteException
import com.ich.reciptopia.domain.model.Post
import com.ich.reciptopia.domain.repository.PostDetailRepository
import javax.inject.Inject

class UnFavoritePostDetailNotLoginUseCase  @Inject constructor(
    private val repository: PostDetailRepository
){
    suspend operator fun invoke(post: Post){
        try{
            repository.unFavoritePostNotLogin(post)
        }catch(e: SQLiteException){
            e.printStackTrace()
        }
    }
}