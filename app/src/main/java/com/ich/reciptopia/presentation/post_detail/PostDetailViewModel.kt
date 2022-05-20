package com.ich.reciptopia.presentation.post_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ich.reciptopia.application.ReciptopiaApplication
import com.ich.reciptopia.common.util.Resource
import com.ich.reciptopia.domain.use_case.post_detail.PostDetailUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
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
        getPost()
        observeUserChanged()
    }

    fun onEvent(event: PostDetailEvent){
        when(event){
            is PostDetailEvent.ClickLike -> {

            }
            is PostDetailEvent.ClickFavorite -> {
                val post = _state.value.curPost!!
                if(post.id != null) {
                    if (post.isFavorite) {
                        unFavoritePost(post.id)
                            .invokeOnCompletion { getPostInfo().invokeOnCompletion { getPost() } }
                    } else {
                        favoritePost(post.id)
                            .invokeOnCompletion { getPostInfo().invokeOnCompletion { getPost() } }
                    }
                }
            }
            is PostDetailEvent.CommentTextChanged -> {
                _state.value = _state.value.copy(
                    commentText = event.text
                )
            }
            is PostDetailEvent.CreateComment -> {

            }
        }
    }

    private fun observeUserChanged() = viewModelScope.launch {
        app.user.collect{ user ->
            _state.value = _state.value.copy(
                currentUser = user
            )
            getPost()
        }
    }

    private fun getPost(){
        getPostInfo().invokeOnCompletion {
            getOwnerOfPost()
            getFavoritePostsForFillingStar()
        }
    }

    private fun getPostInfo() = viewModelScope.launch{
        useCases.getPostInfo(postId).collect{ result ->
            when(result){
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        curPost = result.data!!,
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
                    _eventFlow.emit(UiEvent.ShowToast("포스트 정보를 불러오지 못했습니다 (${result.message})"))
                }
            }
        }
    }

    // call after get current post
    private fun getOwnerOfPost() = viewModelScope.launch {
        useCases.getOwnerOfPost(_state.value.curPost?.ownerId!!).collect{ result ->
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
        useCases.unFavoritePost(userId, postId, userId != null).collect{ result ->
            when(result){
                is Resource.Success -> {
                    _state.value = _state.value.copy(
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

    sealed class UiEvent{
        data class ShowToast(val message: String): UiEvent()
    }
}