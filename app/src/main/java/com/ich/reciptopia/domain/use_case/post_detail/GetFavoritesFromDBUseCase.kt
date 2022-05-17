package com.ich.reciptopia.domain.use_case.post_detail

import com.ich.reciptopia.domain.model.Favorite
import com.ich.reciptopia.domain.repository.PostDetailRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoritesFromDBUseCase @Inject constructor(
    private val repository: PostDetailRepository
) {
    suspend operator fun invoke(): Flow<List<Favorite>> {
        return repository.getFavoritesFromDB()
    }
}