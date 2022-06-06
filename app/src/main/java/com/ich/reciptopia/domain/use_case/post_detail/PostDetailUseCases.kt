package com.ich.reciptopia.domain.use_case.post_detail

import com.ich.reciptopia.domain.use_case.post.*
import com.ich.reciptopia.domain.use_case.post_detail.chat.*

data class PostDetailUseCases(
    val getPostInfo: GetPostInfoUseCase,
    val getOwnerById: GetOwnerByIdUseCase,
    val favoritePost: FavoritePostUseCase,
    val unFavoritePost: UnFavoritePostUseCase,
    val getFavorites: GetFavoritesUseCase,
    val likePost: PostLikeUseCase,
    val unlikePost: PostUnLikeUseCase,
    val getLikeTags: GetPostLikeTagsUseCase,
    val getRecipe: GetRecipeUseCase,
    val getSteps: GetStepsUseCase,
    val getMainIngredients: GetMainIngredientsUseCase,
    val getSubIngredients: GetSubIngredientsUseCase,
    val deletePost: DeletePostUseCase
)
