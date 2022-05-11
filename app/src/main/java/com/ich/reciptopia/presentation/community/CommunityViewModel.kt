package com.ich.reciptopia.presentation.community

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ich.reciptopia.application.ReciptopiaApplication
import com.ich.reciptopia.common.util.Resource
import com.ich.reciptopia.domain.model.Post
import com.ich.reciptopia.domain.model.PostLikeTag
import com.ich.reciptopia.domain.use_case.community.CommunityUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class CommunityViewModel @Inject constructor(
    private val useCases: CommunityUseCases,
    private val app: ReciptopiaApplication
): ViewModel() {

    private val _state = MutableStateFlow(CommunityState())
    val state = _state.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val user by lazy {app.getCurrentUser()?.account}

    init{
        onEvent(CommunityScreenEvent.GetPosts)
    }

    fun onEvent(event: CommunityScreenEvent){
        when(event){
            is CommunityScreenEvent.CreateBoardStateChanged -> {
                _state.value = _state.value.copy(
                    showCreateBoardDialog = event.isOn
                )
            }
            is CommunityScreenEvent.SearchButtonClicked -> {
                val searchModeIsOn = _state.value.searchMode
                if(searchModeIsOn){
                    // 검색 시작
                }else{
                    _state.value = _state.value.copy(
                        searchMode = true
                    )
                }
            }
            is CommunityScreenEvent.SearchModeOff -> {
                _state.value = _state.value.copy(
                    searchMode = false
                )
            }
            is CommunityScreenEvent.SearchQueryChanged -> {
                _state.value = _state.value.copy(
                    searchQuery = event.query
                )
            }
            is CommunityScreenEvent.SortOptionChanged -> {
                _state.value = _state.value.copy(
                    sortOption = event.option
                )
                onEvent(CommunityScreenEvent.GetPosts)
            }
            is CommunityScreenEvent.GetPosts -> {
                val job = when(_state.value.sortOption){
                    "최신순" -> getPostsByTime()
                    "조회순" -> getPostsByViews()
                    else -> throw Exception("sort exception")
                }
                job.invokeOnCompletion {
                    getPostLikeTags().invokeOnCompletion {
                        val tags = _state.value.likeTags
                        val posts = _state.value.posts
                        val likes = MutableList(posts.size){false}
                        for(i in posts.indices){
                            for(j in tags.indices){
                                if(posts[i].id == tags[j].postId && user?.id == tags[j].ownerId){
                                    likes[i] = true
                                    break
                                }
                            }
                        }
                        _state.value = _state.value.copy(
                            like = likes
                        )
                    }
                }
            }
            is CommunityScreenEvent.GetOwnerAccount -> {
                getOwnerOfPost(event.accountId)
            }
        }
    }

    private fun getPostsByTime() = viewModelScope.launch {
        useCases.getPostsByTime().collect{ result ->
            when(result){
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        posts = result.data!!,
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
                    _eventFlow.emit(UiEvent.ShowToast("게시글을 불러오지 못했습니다. (${result.message})"))
                }
            }
        }
    }

    private fun getPostsByViews() = viewModelScope.launch {
        useCases.getPostsByViews().collect{ result ->
            when(result){
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        posts = result.data!!,
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
                    _eventFlow.emit(UiEvent.ShowToast("게시글을 불러오지 못했습니다. (${result.message})"))
                }
            }
        }
    }

    private fun createPost(post: Post) = viewModelScope.launch {
        useCases.createPost(post).collect{ result ->
            when(result){
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        isLoading = false
                    )
                    _eventFlow.emit(UiEvent.ShowToast("게시글을 생성했습니다"))
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
                    _eventFlow.emit(UiEvent.ShowToast("게시글을 불러오지 못했습니다. (${result.message})"))
                }
            }
        }
    }

    private fun getPostLikeTags() = viewModelScope.launch {
        useCases.getPostLikeTags().collect{ result ->
            when(result){
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        likeTags = result.data!!,
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

    private fun postLike(postLikeTag: PostLikeTag) = viewModelScope.launch {
        useCases.postLike(postLikeTag).collect{ result ->
            when(result){
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        isLoading = false
                    )
                    _eventFlow.emit(UiEvent.ShowToast("좋아요를 눌렀습니다"))
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
                    _eventFlow.emit(UiEvent.ShowToast("(${result.message})"))
                }
            }
        }
    }

    private fun getOwnerOfPost(accountId: Long) = viewModelScope.launch {
        useCases.getOwnerOfPost(accountId).collect{ result ->
            when(result){
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        postOwner = result.data!!,
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

    sealed class UiEvent{
        data class ShowToast(val message: String): UiEvent()
    }
}