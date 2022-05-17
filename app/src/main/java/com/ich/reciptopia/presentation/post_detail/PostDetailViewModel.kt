package com.ich.reciptopia.presentation.post_detail

import android.database.sqlite.SQLiteException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ich.reciptopia.common.util.Resource
import com.ich.reciptopia.domain.model.Post
import com.ich.reciptopia.domain.use_case.post_detail.PostDetailUseCases
import com.ich.reciptopia.presentation.community.CommunityScreenEvent
import com.ich.reciptopia.presentation.community.CommunityViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
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
                if (post.favoriteNotLogin) {
                    unFavoritePostNotLogin(post)
                        .invokeOnCompletion { getPostInfo().invokeOnCompletion { getOwnerOfPost() } }
                } else {
                    favoritePostNotLogin(post)
                        .invokeOnCompletion { getPostInfo().invokeOnCompletion { getOwnerOfPost() } }
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

    private fun favoritePostNotLogin(post: Post) = viewModelScope.launch {
        try {
            useCases.favoritePostNotLogin(post)
        } catch (e: SQLiteException) {
            _eventFlow.emit(UiEvent.ShowToast("즐겨찾기 등록에 실패했습니다"))
        }
    }

    private fun unFavoritePostNotLogin(post: Post) = viewModelScope.launch {
        try {
            useCases.unFavoritePostNotLogin(post)
        } catch (e: SQLiteException) {
            _eventFlow.emit(UiEvent.ShowToast("즐겨찾기 제거에 실패했습니다"))
        }
    }

    private fun getFavoritePostsForFillingStar() = viewModelScope.launch {
        useCases.getFavoritePosts().collect { result ->
            for(entity in result){
                if(entity.post.id == _state.value.curPost?.id){
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