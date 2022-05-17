package com.ich.reciptopia.presentation.post_detail

import android.database.sqlite.SQLiteException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    private val useCases: PostDetailUseCases
): ViewModel() {

    private val _state = MutableStateFlow(PostDetailState())
    val state = _state.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var postId = -1L

    fun initialize(postId: Long){
        this.postId = postId
        getPostInfo().invokeOnCompletion {
            getOwnerOfPost()
            getFavoritePostsForFillingStar()
        }
    }

    fun onEvent(event: PostDetailEvent){
        when(event){
            is PostDetailEvent.ClickLike -> {

            }
            is PostDetailEvent.ClickFavorite -> {
                val post = _state.value.curPost!!
                if(post.id != null) {
                    if (post.favoriteNotLogin) {
                        unFavoritePostNotLogin(post.id)
                            .invokeOnCompletion { getPostInfo().invokeOnCompletion { getOwnerOfPost() } }
                    } else {
                        favoritePostNotLogin(post.id)
                            .invokeOnCompletion { getPostInfo().invokeOnCompletion { getOwnerOfPost() } }
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

    private fun favoritePostNotLogin(postId: Long) = viewModelScope.launch {
        try {
            useCases.favoritePostNotLogin(postId)
        } catch (e: SQLiteException) {
            _eventFlow.emit(UiEvent.ShowToast("즐겨찾기 등록에 실패했습니다"))
        }
    }

    private fun unFavoritePostNotLogin(postId: Long) = viewModelScope.launch {
        try {
            useCases.unFavoritePostNotLogin(postId)
        } catch (e: SQLiteException) {
            _eventFlow.emit(UiEvent.ShowToast("즐겨찾기 제거에 실패했습니다"))
        }
    }

    private fun getFavoritePostsForFillingStar() = viewModelScope.launch {
        useCases.getFavoritesFromDB().collect { result ->
            for(favorite in result){
                if(favorite.postId == _state.value.curPost?.id){
                    val post = _state.value.curPost?.copy(
                        favoriteNotLogin = true
                    )
                    _state.value = _state.value.copy(
                        curPost = post
                    )
                    break
                }
            }
        }
    }

    sealed class UiEvent{
        data class ShowToast(val message: String): UiEvent()
    }
}