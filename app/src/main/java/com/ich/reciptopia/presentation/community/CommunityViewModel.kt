package com.ich.reciptopia.presentation.community

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ich.reciptopia.application.ReciptopiaApplication
import com.ich.reciptopia.common.util.Resource
import com.ich.reciptopia.common.util.getAddedList
import com.ich.reciptopia.common.util.getRemovedList
import com.ich.reciptopia.domain.model.Account
import com.ich.reciptopia.domain.model.Post
import com.ich.reciptopia.domain.model.PostLikeTag
import com.ich.reciptopia.domain.use_case.community.CommunityUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
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
            is CommunityScreenEvent.CreatePostStateChanged -> {
                _state.value = _state.value.copy(
                    showCreatePostDialog = event.isOn
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
            is CommunityScreenEvent.CreatePostTitleChanged -> {
                _state.value = _state.value.copy(
                    newPostTitle = event.title
                )
            }
            is CommunityScreenEvent.CreatePostContentChanged -> {
                _state.value = _state.value.copy(
                    newPostContent = event.content
                )
            }
            is CommunityScreenEvent.CreatePostAddImage -> {
                _state.value = _state.value.copy(
                    newPictureUrls = _state.value.newPictureUrls.getAddedList(event.uri)
                )
            }
            is CommunityScreenEvent.CreatePostRemoveImage -> {
                _state.value = _state.value.copy(
                    newPictureUrls = _state.value.newPictureUrls.getRemovedList(event.idx)
                )
            }
            is CommunityScreenEvent.GetPosts -> {
                val job = when(_state.value.sortOption){
                    "최신순" -> getPostsByTime()
                    "조회순" -> getPostsByViews()
                    else -> throw Exception("sort exception")
                }
                job.invokeOnCompletion {
                    getPostLikeTags()
                    _state.value.posts.forEachIndexed{ i, post ->
                        getOwnerOfPost(i, post.ownerId!!)
                    }
                }
            }
            is CommunityScreenEvent.CreatePost -> {
                val newPost = Post(
                    ownerId = user?.id,
                    title = _state.value.newPostTitle,
                    content = _state.value.newPostContent,
                    pictureUrls = _state.value.newPictureUrls,
                    views = 0L
                )
                createPost(newPost)
                    .invokeOnCompletion { onEvent(CommunityScreenEvent.GetPosts) }
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
                    _eventFlow.emit(UiEvent.SuccessCreatePost)
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

    private fun getDbFavoritesForFillingStar(){
        viewModelScope.launch {
            val favoriteEntities = useCases.getFavoriteEntities().first()
            val map = mutableMapOf<Long,Boolean>()

            for(entity in favoriteEntities){
                map[entity.post.id!!] = true
            }

            for(post in _state.value.posts){
                if(map[post.id] == true){

                }
            }
        }
    }

    // call after get Posts
    private fun getOwnerOfPost(postIdx: Int, accountId: Long) = viewModelScope.launch {
        useCases.getOwnerOfPost(accountId).collect{ result ->
            when(result){
                is Resource.Success -> {
                    val posts = _state.value.posts.toMutableList()
                    val postWithAccount = posts[postIdx].copy(
                        owner = result.data
                    )
                    posts[postIdx] = postWithAccount
                    _state.value = _state.value.copy(
                        posts = posts,
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
        object SuccessCreatePost: UiEvent()
    }
}