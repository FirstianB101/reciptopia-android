package com.ich.reciptopia.presentation.community

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ich.reciptopia.application.ReciptopiaApplication
import com.ich.reciptopia.common.util.Resource
import com.ich.reciptopia.common.util.getAddedList
import com.ich.reciptopia.common.util.getRemovedList
import com.ich.reciptopia.domain.model.*
import com.ich.reciptopia.domain.use_case.community.CommunityUseCases
import com.ich.reciptopia.presentation.main.search.util.ChipState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommunityViewModel @Inject constructor(
    private val useCases: CommunityUseCases,
    private val app: ReciptopiaApplication
) : ViewModel() {

    private val _state = MutableStateFlow(CommunityState())
    val state = _state.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _isRefreshing = mutableStateOf(false)
    val isRefreshing: State<Boolean> = _isRefreshing

    init {
        observeUserChanged()
        onEvent(CommunityScreenEvent.SearchPosts)
    }

    fun onEvent(event: CommunityScreenEvent) {
        when (event) {
            is CommunityScreenEvent.CreatePostStateChanged -> {
                if(_state.value.currentUser != null) {
                    _state.value = _state.value.copy(
                        showCreatePostDialog = event.isOn
                    )
                }else{
                    viewModelScope.launch { 
                        _eventFlow.emit(UiEvent.ShowToast("글 작성에는 로그인이 필요합니다"))
                    }
                }
            }
            is CommunityScreenEvent.SearchButtonClicked -> {
                val searchModeIsOn = _state.value.searchMode
                if (searchModeIsOn) {
                    onEvent(CommunityScreenEvent.SearchPosts)
                } else {
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
                onEvent(CommunityScreenEvent.SearchPosts)
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
            is CommunityScreenEvent.CreatePostStepChanged -> {
                _state.value = _state.value.copy(
                    newPostStep = event.step
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
            is CommunityScreenEvent.AddChip -> {
                val newChip = ChipState(event.chipText, mutableStateOf(true), event.detail)
                _state.value = _state.value.copy(
                    newPostChips = _state.value.newPostChips.getAddedList(newChip)
                )
            }
            is CommunityScreenEvent.RemoveChip -> {
                val chip = _state.value.newPostChips[event.idx]
                _state.value = _state.value.copy(
                    newPostChips = _state.value.newPostChips.getRemovedList(chip)
                )
            }
            is CommunityScreenEvent.ClickChip -> {
                val chips = _state.value.newPostChips.toMutableList()
                chips[event.idx].isSubIngredient.value = !chips[event.idx].isSubIngredient.value
                _state.value = _state.value.copy(
                    newPostChips = chips
                )
            }
            is CommunityScreenEvent.AddChipDialogStateChanged -> {
                _state.value = _state.value.copy(
                    showAddChipDialog = event.show
                )
            }
            is CommunityScreenEvent.SearchPosts -> {
                val job = when (_state.value.sortOption) {
                    "최신순" -> getPostsByTime()
                    "조회순" -> getPostsByViews()
                    else -> throw Exception("sort exception")
                }
                job.invokeOnCompletion {
                    getFavoritesForFillingStar()
                    getPostLikeTags()
                    _state.value.posts.forEachIndexed { i, post ->
                        getOwnerOfPost(i, post.ownerId!!)
                    }
                }
            }
            is CommunityScreenEvent.CreatePost -> {
                val mainIngredients = mutableListOf<MainIngredient>()
                val subIngredients = mutableListOf<SubIngredient>()

                _state.value.newPostChips.forEach { chipState ->
                    if(chipState.isSubIngredient.value){
                        subIngredients.add(SubIngredient(name = chipState.text, detail = chipState.detail))
                    }else{
                        mainIngredients.add(MainIngredient(name = chipState.text, detail = chipState.detail))
                    }
                }
                val steps = _state.value.newPostStep.split('\n')
                    .filter{ it.isNotBlank() }
                    .map{ Step(description = it, pictureUrl = null) }
                val newPost = RecipePost(
                    post = Post(
                        ownerId = _state.value.currentUser?.account?.id,
                        title = _state.value.newPostTitle,
                        content = _state.value.newPostContent,
                        pictureUrls = _state.value.newPictureUrls
                    ),
                    mainIngredients = RecipePostMainIngredients(mainIngredients),
                    subIngredients = RecipePostSubIngredients(subIngredients),
                    steps = RecipePostSteps(steps)
                )
                createRecipePost(newPost)
            }
            is CommunityScreenEvent.FavoriteButtonClicked -> {
                if (event.post.isFavorite)
                    unFavoritePost(event.post.id!!, event.idx)
                else
                    favoritePost(event.post.id!!, event.idx)
            }
            is CommunityScreenEvent.LikeButtonClicked -> {
                if(_state.value.currentUser != null) {
                    if (event.post.like)
                        unlikePost(event.post.id!!, event.idx)
                    else
                        likePost(event.post.id!!, event.idx)
                }else{
                    viewModelScope.launch {
                        _eventFlow.emit(UiEvent.ShowToast("좋아요를 표시하려면 로그인 해주세요"))
                    }
                }
            }
        }
    }

    private fun observeUserChanged() = viewModelScope.launch {
        app.user.collect{ user ->
            _state.value = _state.value.copy(
                currentUser = user
            )
            onEvent(CommunityScreenEvent.SearchPosts)
        }
    }

    private fun getPostsByTime() = viewModelScope.launch {
        val searchQuery = _state.value.searchQuery
        useCases.getPostsByTime(searchQuery).collect { result ->
            when (result) {
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
        val searchQuery = _state.value.searchQuery
        useCases.getPostsByViews(searchQuery).collect { result ->
            when (result) {
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

    private fun createRecipePost(recipePost: RecipePost) = viewModelScope.launch {
        useCases.createRecipePost(recipePost).collect { result ->
            when (result) {
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
        val userId = _state.value.currentUser?.account?.id
        if(userId != null) {
            useCases.getPostLikeTags(userId).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            likeTags = result.data!!,
                            isLoading = false
                        )

                        val map = mutableMapOf<Long, PostLikeTag>()

                        for (tag in result.data) {
                            map[tag.postId!!] = tag
                        }

                        val posts = _state.value.posts.toMutableList()
                        posts.forEachIndexed { index, post ->
                            if (map[post.id] != null) {
                                posts[index] = post.copy(
                                    like = true
                                )
                            }
                        }

                        _state.value = _state.value.copy(
                            isLoading = false,
                            posts = posts
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

    private fun likePost(postId: Long, idx: Int) = viewModelScope.launch {
        val userId = _state.value.currentUser?.account?.id!!
        useCases.likePost(userId, postId).collect { result ->
            when (result) {
                is Resource.Success -> {
                    val posts = _state.value.posts.toMutableList()
                    posts[idx] = posts[idx].copy(
                        like = true
                    )
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
                    _eventFlow.emit(UiEvent.ShowToast("좋아요를 표시하지 못했습니다(${result.message})"))
                }
            }
        }
    }

    private fun unlikePost(postId: Long, idx: Int) = viewModelScope.launch {
        val likeTagId = _state.value.likeTags.find {it.postId == postId}?.id!!
        useCases.unlikePost(likeTagId).collect { result ->
            when (result) {
                is Resource.Success -> {
                    val posts = _state.value.posts.toMutableList()
                    posts[idx] = posts[idx].copy(
                        like = false
                    )
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
                    _eventFlow.emit(UiEvent.ShowToast("좋아요를 취소하지 못했습니다(${result.message})"))
                }
            }
        }
    }

    private fun getFavoritesForFillingStar() = viewModelScope.launch {
        val userId = _state.value.currentUser?.account?.id
        useCases.getFavorites(userId).collect { result ->
            when(result){
                is Resource.Success -> {
                    val map = mutableMapOf<Long, Favorite>()

                    for (favorite in result.data!!) {
                        map[favorite.postId!!] = favorite
                    }

                    val posts = _state.value.posts.toMutableList()
                    posts.forEachIndexed { index, post ->
                        if (map[post.id] != null) {
                            posts[index] = post.copy(
                                isFavorite = true
                            )
                        }
                    }

                    _state.value = _state.value.copy(
                        favorites = result.data,
                        isLoading = false,
                        posts = posts
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
                    _eventFlow.emit(UiEvent.ShowToast("즐겨찾기 정보를 불러오지 못했습니다 (${result.message})"))
                }
            }
        }
    }

    private fun favoritePost(postId: Long, idx: Int) = viewModelScope.launch {
        val ownerId = _state.value.currentUser?.account?.id
        useCases.favoritePost(ownerId, postId, ownerId != null).collect{ result ->
            when(result){
                is Resource.Success -> {
                    val posts = _state.value.posts.toMutableList()
                    posts[idx] = posts[idx].copy(
                        isFavorite = true
                    )
                    _state.value = _state.value.copy(
                        posts = posts,
                        isLoading = false
                    )
                    _eventFlow.emit(UiEvent.ShowToast("즐겨찾기를 추가했습니다"))
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
                    _eventFlow.emit(UiEvent.ShowToast("즐겨찾기를 추가하지 못했습니다 (${result.message})"))
                }
            }
        }
    }

    private fun unFavoritePost(postId: Long, idx: Int) = viewModelScope.launch {
        val ownerId = _state.value.currentUser?.account?.id
        val favoriteId = _state.value.favorites.find{it.postId == postId}?.id
        useCases.unFavoritePost(postId, favoriteId,ownerId != null).collect{ result ->
            when(result){
                is Resource.Success -> {
                    val posts = _state.value.posts.toMutableList()
                    posts[idx] = posts[idx].copy(
                        isFavorite = false
                    )
                    _state.value = _state.value.copy(
                        posts = posts,
                        isLoading = false
                    )
                    _eventFlow.emit(UiEvent.ShowToast("즐겨찾기를 제거했습니다"))
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

    // call after get Posts
    private fun getOwnerOfPost(postIdx: Int, accountId: Long) = viewModelScope.launch {
        useCases.getOwnerOfPost(accountId).collect { result ->
            when (result) {
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

    sealed class UiEvent {
        data class ShowToast(val message: String) : UiEvent()
        object SuccessCreatePost : UiEvent()
    }
}