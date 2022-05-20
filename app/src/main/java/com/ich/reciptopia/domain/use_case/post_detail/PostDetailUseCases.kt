package com.ich.reciptopia.domain.use_case.post_detail

import com.ich.reciptopia.domain.use_case.post_list.FavoritePostUseCase
import com.ich.reciptopia.domain.use_case.post_list.GetFavoritesUseCase
import com.ich.reciptopia.domain.use_case.post_list.GetOwnerOfPostUseCase
import com.ich.reciptopia.domain.use_case.post_list.UnFavoritePostUseCase

data class PostDetailUseCases(
    val getPostInfo: GetPostInfoUseCase,
    val getOwnerOfPost: GetOwnerOfPostUseCase,
    val favoritePost: FavoritePostUseCase,
    val unFavoritePost: UnFavoritePostUseCase,
    val getFavorites: GetFavoritesUseCase
)
