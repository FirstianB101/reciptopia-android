package com.ich.reciptopia.presentation.post_detail

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
        getPostInfo()
    }

    fun onEvent(event: PostDetailEvent){
        when(event){
            is PostDetailEvent.ClickLike -> {

            }
            is PostDetailEvent.ClickFavorite -> {

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

    sealed class UiEvent{
        data class ShowToast(val message: String): UiEvent()
    }
}