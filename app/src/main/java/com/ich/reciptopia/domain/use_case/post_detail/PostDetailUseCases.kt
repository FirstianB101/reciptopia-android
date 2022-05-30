package com.ich.reciptopia.domain.use_case.post_detail

import com.ich.reciptopia.domain.use_case.post.*

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
    val getComments: GetCommentsUseCase,
    val getReplies: GetRepliesUseCase,
    val createComment: CreateCommentUseCase,
    val getCommentLikeTags: GetCommentLikeTagsUseCase,
    val getReplyLikeTags: GetReplyLikeTagsUseCase,
    val likeComment: LikeCommentUseCase,
    val unlikeComment: UnLikeCommentUseCase,
    val likeReply: LikeReplyUseCase,
    val unlikeReply: UnLikeReplyUseCase
)
