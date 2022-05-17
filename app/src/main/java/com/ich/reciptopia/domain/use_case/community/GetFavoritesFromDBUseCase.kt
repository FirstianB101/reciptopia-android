package com.ich.reciptopia.domain.use_case.community

import com.ich.reciptopia.domain.model.Favorite
import com.ich.reciptopia.domain.repository.CommunityRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoritesFromDBUseCase  @Inject constructor(
    private val repository: CommunityRepository
){
    suspend operator fun invoke(): Flow<List<Favorite>> {
        return repository.getFavoritesFromDB()
    }
}