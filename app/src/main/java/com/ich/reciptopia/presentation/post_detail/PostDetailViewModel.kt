package com.ich.reciptopia.presentation.post_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ich.reciptopia.application.ReciptopiaApplication
import com.ich.reciptopia.common.util.Resource
import com.ich.reciptopia.domain.model.PostLikeTag
import com.ich.reciptopia.domain.use_case.post_detail.PostDetailUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostDetailViewModel @Inject constructor(
    private val useCases: PostDetailUseCases,
    private val app: ReciptopiaApplication
): ViewModel() {

    private val _state = MutableStateFlow(PostDetailState())
    val state = _state.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var postId = -1L

    fun initialize(postId: Long){
        this.postId = postId
        observeUserChanged()
        fillCurPostInfo()
    }

    fun onEvent(event: PostDetailEvent){
        when(event){
            is PostDetailEvent.ClickLike -> {
                doIfLogin {
                    val isLike = _state.value.curPost?.like == true
                    if(isLike) unlikePost() else likePost()
                }
            }
            is PostDetailEvent.ClickFavorite -> {
                viewModelScope.launch {
                    val post = _state.value.curPost!!
                    if(post.id != null) {
                        if (post.isFavorite)
                            unFavoritePost(post.id)
                        else
                            favoritePost(post.id)
                    }
                }
            }
            is PostDetailEvent.ShowSettingMenu -> {
                _state.value = _state.value.copy(
                    showSettingMenu = event.show
                )
            }
            is PostDetailEvent.DeletePost -> {
                deletePost()
            }
        }
    }

    private fun doIfLogin(function: suspend () -> Unit) = viewModelScope.launch{
        val isLogin = _state.value.currentUser != null
        if(isLogin){
            function()
        }else{
            _eventFlow.emit(UiEvent.ShowToast("로그인이 필요합니다"))
        }
    }


    private fun observeUserChanged() = viewModelScope.launch {
        app.user.collect{ user ->
            _state.value = _state.value.copy(
                currentUser = user
            )
            fillCurPostInfo()
        }
    }

    private fun fillCurPostInfo() = viewModelScope.launch{
        getPostInfo().join()
        getOwnerById()
        getFavoritePostsForFillingStar()
        getPostLikeTagsForFillingThumb()
        getRecipe(postId).join()
        getMainIngredients(postId)
        getSubIngredients()
        getSteps()
    }

    private fun getPostInfo() = viewModelScope.launch{
        useCases.getPostInfo(postId).collect{ result ->
            when(result){
                is Resource.Success -> {
                    val isOwner = _state.value.currentUser?.account?.id == result.data?.ownerId
                    _state.value = _state.value.copy(
                        curPost = result.data!!,
                        isLoading = false,
                        isOwner = isOwner
                    )
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(
                        isLoading = true
                    )
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false
                    )
                    _eventFlow.emit(UiEvent.ShowToast("포스트 정보를 불러오지 못했습니다 (${result.message})"))
                }
            }
        }
    }

    // call after get current post
    private fun getOwnerById() = viewModelScope.launch {
        val ownerId = _state.value.curPost?.ownerId!!
        useCases.getOwnerById(ownerId).collect{ result ->
            if(result is Resource.Success){
                val post = _state.value.curPost?.copy(
                    owner = result.data
                )

                _state.value = _state.value.copy(
                    curPost = post
                )
            }
        }
    }

    private fun favoritePost(postId: Long) = viewModelScope.launch {
        val userId = _state.value.currentUser?.account?.id
        useCases.favoritePost(userId, postId, userId != null).collect{ result ->
            when(result){
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        curPost = _state.value.curPost?.copy(isFavorite = true),
                        curPostFavorite = result.data,
                        isLoading = false
                    )
                    _eventFlow.emit(UiEvent.ShowToast("즐겨찾기에 추가되었습니다"))
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(
                        isLoading = true
                    )
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false
                    )
                    _eventFlow.emit(UiEvent.ShowToast("즐겨찾기에 추가하지 못했습니다 (${result.message})"))
                }
            }
        }
    }

    private fun unFavoritePost(postId: Long) = viewModelScope.launch {
        val userId = _state.value.currentUser?.account?.id
        val favoriteId = _state.value.curPostFavorite?.id
        useCases.unFavoritePost(postId, favoriteId, userId != null).collect{ result ->
            when(result){
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        curPost = _state.value.curPost?.copy(isFavorite = false),
                        curPostFavorite = null,
                        isLoading = false
                    )
                    _eventFlow.emit(UiEvent.ShowToast("즐겨찾기가 제거되었습니다"))
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(
                        isLoading = true
                    )
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false
                    )
                    _eventFlow.emit(UiEvent.ShowToast("즐겨찾기를 제거하지 못했습니다 (${result.message})"))
                }
            }
        }
    }

    private fun getFavoritePostsForFillingStar() = viewModelScope.launch {
        val userId = _state.value.currentUser?.account?.id
        useCases.getFavorites(userId).collect { result ->
            when(result){
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        isLoading = false
                    )

                    for(favorite in result.data!!){
                        if(favorite.postId == _state.value.curPost?.id){
                            val post = _state.value.curPost?.copy(
                                isFavorite = true
                            )
                            _state.value = _state.value.copy(
                                curPostFavorite = favorite,
                                curPost = post
                            )
                            break
                        }
                    }
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(
                        isLoading = true
                    )
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false
                    )
                }
            }
        }
    }

    private fun likePost() = viewModelScope.launch {
        val userId = _state.value.currentUser?.account?.id!!
        useCases.likePost(userId, postId).collect { result ->
            when (result) {
                is Resource.Success -> {
                    val post = _state.value.curPost
                    _state.value = _state.value.copy(
                        curPost = post?.copy(like = true, likeCount = post.likeCount?.plus(1)),
                        curPostLikeTag = result.data!!,
                        isLoading = false
                    )
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(
                        isLoading = true
                    )
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false
                    )
                    _eventFlow.emit(UiEvent.ShowToast("좋아요를 표시하지 못했습니다(${result.message})"))
                }
            }
        }
    }

    private fun unlikePost() = viewModelScope.launch {
        val likeTagId = _state.value.curPostLikeTag?.id!!
        useCases.unlikePost(likeTagId).collect { result ->
            when (result) {
                is Resource.Success -> {
                    val post = _state.value.curPost
                    _state.value = _state.value.copy(
                        curPost = post?.copy(like = false, likeCount = post.likeCount?.minus(1)),
                        curPostLikeTag = null,
                        isLoading = false
                    )
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(
                        isLoading = true
                    )
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false
                    )
                    _eventFlow.emit(UiEvent.ShowToast("좋아요를 취소하지 못했습니다(${result.message})"))
                }
            }
        }
    }

    private fun getPostLikeTagsForFillingThumb() = viewModelScope.launch {
        val userId = _state.value.currentUser?.account?.id
        if(userId != null) {
            useCases.getLikeTags(userId).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        val tags = result.data!!
                        val post = _state.value.curPost
                        var like = false
                        var likeTag: PostLikeTag? = null

                        tags.forEach {
                            if (it.ownerId == userId && it.postId == post?.id) {
                                like = true
                                likeTag = it
                            }
                        }

                        _state.value = _state.value.copy(
                            curPostLikeTag = likeTag,
                            curPost = post?.copy(like = like),
                            isLoading = false
                        )
                    }
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(
                            isLoading = true
                        )
                    }
                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            isLoading = false
                        )
                    }
                }
            }
        }
    }

    private fun getRecipe(postId: Long) = viewModelScope.launch {
        useCases.getRecipe(postId).collect{ result ->
            when(result){
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        curRecipe = result.data,
                        isLoading = false
                    )
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(
                        isLoading = true
                    )
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false
                    )
                }
            }
        }
    }

    private fun getMainIngredients(postId: Long) = viewModelScope.launch {
        useCases.getMainIngredients(postId).collect { result ->
            when(result){
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        mainIngredients = result.data!!,
                        isLoading = false
                    )
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(
                        isLoading = true
                    )
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false
                    )
                }
            }
        }
    }

    private fun getSubIngredients() = viewModelScope.launch {
        val recipeId = _state.value.curRecipe?.id
        useCases.getSubIngredients(recipeId!!).collect { result ->
            when(result){
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        subIngredients = result.data!!,
                        isLoading = false
                    )
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(
                        isLoading = true
                    )
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false
                    )
                }
            }
        }
    }

    private fun getSteps() = viewModelScope.launch {
        val recipeId = state.value.curRecipe?.id!!
        useCases.getSteps(recipeId).collect { result ->
            when(result){
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        curPostSteps = result.data!!,
                        isLoading = false
                    )
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(
                        isLoading = true
                    )
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false
                    )
                }
            }
        }
    }

    private fun deletePost() = viewModelScope.launch {
        val postId = _state.value.curPost?.id!!
        useCases.deletePost(postId).collect{ result ->
            when(result){
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        isLoading = false
                    )
                    _eventFlow.emit(UiEvent.ShowToast("게시글이 삭제되었습니다"))
                    _eventFlow.emit(UiEvent.GoToPostList)
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(
                        isLoading = true
                    )
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false
                    )
                    _eventFlow.emit(UiEvent.ShowToast("게시글을 삭제하지 못했습니다 (${result.message})"))
                }
            }
        }
    }

    sealed class UiEvent{
        data class ShowToast(val message: String): UiEvent()
        object GoToPostList: UiEvent()
    }
}