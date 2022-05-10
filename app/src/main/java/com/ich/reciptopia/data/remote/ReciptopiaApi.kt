package com.ich.reciptopia.data.remote

import com.ich.reciptopia.data.remote.dto.*
import com.ich.reciptopia.domain.model.*
import retrofit2.Response
import retrofit2.http.*

interface ReciptopiaApi {

    @POST("auth/token")
    suspend fun getToken(@Body auth: Auth): UserDto

    @GET("auth/me")
    suspend fun authorizeUser(): UserDto

    // Account
    @GET("accounts/id")
    suspend fun getAccount(): AccountDto

    @GET("accounts")
    suspend fun getAccounts(): List<AccountDto>

    @POST("accounts")
    suspend fun createAccount(@Body account: Account): AccountDto

    @GET("accounts/{email}/exists")
    suspend fun accountExists(@Path("email") email: String): Exist

    @DELETE("accounts/{id}")
    suspend fun deleteAccount(@Path("id")accountId: Long): Response<Unit>


    // Post
    @GET("posts/{id}")
    suspend fun getPost(): PostDto

    @GET("posts")
    suspend fun getPosts(): List<PostDto>

    @POST("posts")
    suspend fun createPost(@Body post: Post): PostDto

    @PATCH("posts/{id}")
    suspend fun patchPost(@Path("id")postId: Long, post: Post): PostDto

    @DELETE("posts/{id}")
    suspend fun deletePost(@Path("id")postId: Long): Response<Unit>


    // Comment
    @GET("post/comments/{id}")
    suspend fun getComment(@Path("id")commentId: Long): CommentDto

    @GET("post/comments")
    suspend fun getComments(): List<CommentDto>

    @POST("post/comments")
    suspend fun createComment(@Body comment: Comment): CommentDto

    @PATCH("post/comments/{id}")
    suspend fun patchComment(@Path("id")commentId: Long, comment: Comment): CommentDto

    @DELETE("post/comments/{id}")
    suspend fun deleteComment(@Path("id")commentId: Long): Response<Unit>


    // Reply
    @GET("post/comment/replies/{id}")
    suspend fun getReply(@Path("id")replyId: Long): ReplyDto

    @GET("post/comment/replies")
    suspend fun getReplies(): List<ReplyDto>

    @POST("post/comment/replies")
    suspend fun createReply(@Body reply: Reply): ReplyDto

    @PATCH("post/comment/replies/{id}")
    suspend fun patchReply(@Path("id")replyId: Long,@Body reply: Reply): ReplyDto

    @DELETE("post/comment/replies/{id}")
    suspend fun deleteReply(@Path("id")replyId: Long): Response<Unit>


    // Step
    @GET("post/recipeId/steps/{id}")
    suspend fun getStep(@Path("id")stepId: Long): StepDto

    @POST("post/recipeId/steps")
    suspend fun createStep(@Body step: Step): StepDto

    @PATCH("post/recipeId/steps/{id}")
    suspend fun patchStep(@Body step: Step): StepDto

    @DELETE("post/recipeId/steps/{id}")
    suspend fun deleteStep(@Path("id")stepId: Long): Response<Unit>


    // MainIngredient
    @GET("post/recipe/mainIngredients/{id}")
    suspend fun getMainIngredient(@Path("id")ingredientId: Long): MainIngredientDto

    @POST("post/recipe/mainIngredients")
    suspend fun createMainIngredient(@Body ingredient: MainIngredient): MainIngredientDto

    @PATCH("post/recipe/mainIngredients/{id}")
    suspend fun patchMainIngredient(@Path("id")ingredientId: Long, @Body ingredient: MainIngredient): MainIngredientDto

    @DELETE("post/recipe/mainIngredients/{id}")
    suspend fun deleteMainIngredient(@Path("id")ingredientId: Long): Response<Unit>


    // SubIngredient
    @GET("post/recipe/subIngredients/{id}")
    suspend fun getSubIngredient(@Path("id")ingredientId: Long): SubIngredientDto

    @POST("post/recipe/subIngredients")
    suspend fun createSubIngredient(@Body ingredient: SubIngredient): SubIngredientDto

    @PATCH("post/recipe/subIngredients/{id}")
    suspend fun patchSubIngredient(@Path("id")ingredientId: Long, @Body ingredient: SubIngredient): SubIngredientDto

    @DELETE("post/recipe/subIngredients/{id}")
    suspend fun deleteSubIngredient(@Path("id")ingredientId: Long): Response<Unit>


    // Recipe
    @GET("post/recipes/{id}")
    suspend fun getRecipe(@Path("id")recipeId: Long): RecipeDto

    @POST("post/recipes")
    suspend fun createRecipe(@Body recipe: Recipe): RecipeDto

    @PATCH("post/recipes/{id}")
    suspend fun patchRecipe(@Path("id")recipeId: Long, @Body recipe: Recipe): RecipeDto

    @DELETE("post/recipes/{id}")
    suspend fun deleteRecipe(@Path("id")recipeId: Long): Response<Unit>


    // PostLikeTag
    @GET("post/likeTags/{id}")
    suspend fun getPostLikeTag(@Path("id")tagId: Long): PostLikeTagDto

    @GET("post/likeTags")
    suspend fun getPostLikeTags(): List<PostLikeTagDto>

    @POST("post/likeTags")
    suspend fun createPostLikeTag(@Body tag: PostLikeTag): PostLikeTagDto

    @DELETE("post/likeTags/{id}")
    suspend fun deletePostLikeTag(@Path("id")tagId: Long): Response<Unit>


    // CommentLikeTag
    @GET("post/comment/likeTags/{id}")
    suspend fun getCommentLikeTag(@Path("id")tagId: Long): CommentLikeTagDto

    @GET("post/comment/likeTags")
    suspend fun getCommentLikeTags(): List<CommentLikeTagDto>

    @POST("post/comment/likeTags")
    suspend fun createCommentLikeTag(@Body tag: CommentLikeTag): CommentLikeTagDto

    @DELETE("post/comment/likeTags/{id}")
    suspend fun deleteCommentLikeTag(@Path("id")tagId: Long): Response<Unit>


    // ReplyLikeTag
    @GET("post/comment/reply/likeTags/{id}")
    suspend fun getReplyLikeTag(@Path("id")tagId: Long): ReplyLikeTagDto

    @GET("post/comment/reply/likeTags")
    suspend fun getReplyLikeTags(): List<ReplyLikeTagDto>

    @POST("post/comment/reply/likeTags")
    suspend fun createReplyLikeTag(@Body tag: ReplyLikeTag): ReplyLikeTagDto

    @DELETE("post/comment/reply/likeTags/{id}")
    suspend fun deleteReplyLikeTag(@Path("id")tagId: Long): Response<Unit>


    // SearchHistory
    @GET("account/searchHistories/{id}")
    suspend fun getSearchHistory(@Path("id")historyId: Long): SearchHistoryDto

    @GET("account/{ownerId}/searchHistories")
    suspend fun getSearchHistories(): List<SearchHistoryDto>

    @POST("account/searchHistories")
    suspend fun createSearchHistory(@Body history: SearchHistory): SearchHistoryDto

    @DELETE("account/searchHistories/{id}")
    suspend fun deleteSearchHistory(@Path("id")historyId: Long): Response<Unit>


    // Favorite
    @GET("account/favorites/{id}")
    suspend fun getFavorite(@Path("id")favoriteId: Long): FavoriteDto

    @GET("account/favorites")
    suspend fun getFavorites(): List<FavoriteDto>

    @POST("account/favorites")
    suspend fun createFavorite(@Body favorite: Favorite): FavoriteDto

    @DELETE("account/favorites/{id}")
    suspend fun deleteFavorite(@Path("id")favoriteId: Long): Response<Unit>
}