package com.ich.reciptopia.domain.use_case.post_detail

import com.ich.reciptopia.domain.model.FavoriteEntity
import com.ich.reciptopia.domain.repository.PostDetailRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteEntitiesUseCase @Inject constructor(
    private val repository: PostDetailRepository
) {
    suspend operator fun invoke(): Flow<List<FavoriteEntity>> {
        return repository.getFavoriteEntities()
    }
}