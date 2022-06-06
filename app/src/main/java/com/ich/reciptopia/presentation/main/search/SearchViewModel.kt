package com.ich.reciptopia.presentation.main.search

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ich.reciptopia.application.ReciptopiaApplication
import com.ich.reciptopia.common.util.Resource
import com.ich.reciptopia.domain.model.Favorite
import com.ich.reciptopia.domain.model.Post
import com.ich.reciptopia.domain.model.PostLikeTag
import com.ich.reciptopia.domain.model.SearchHistory
import com.ich.reciptopia.domain.use_case.search.SearchUseCases
import com.ich.reciptopia.presentation.main.search.util.ChipState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val useCases: SearchUseCases,
    private val app: ReciptopiaApplication
) : ViewModel() {

    private val _state = MutableStateFlow(SearchState())
    val state: StateFlow<SearchState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _isRefreshing = mutableStateOf(false)
    val isRefreshing: State<Boolean> = _isRefreshing

    init {
        observeUserChanged()
    }

    fun onEvent(event: SearchScreenEvent) {
        when (event) {
            is SearchScreenEvent.RefreshResultList -> {
                getSearchedPostList()
            }
            is SearchScreenEvent.DoSearch -> {
                _state.value = _state.value.copy(
                    chipsForSearch = event.ingredients
                )

                viewModelScope.launch {
                    if(event.ingredients.isNotEmpty()) {
                        getSearchedPostList()
                        val newHistory = SearchHistory(
                            ownerId = _state.value.currentUser?.account?.id,
                            ingredientNames = event.ingredients.map{it.text},
                            isSubIngredient = event.ingredients.map{it.isSubIngredient.value}
                        )
                        addSearchHistory(newHistory).join()
                        getSearchHistories()
                        
                        _eventFlow.emit(UiEvent.NavigateToSearchResultScreen)
                    }else{
                        _eventFlow.emit(UiEvent.ShowToast("검색할 재료들을 추가해주세요"))
                    }
                }
            }
            is SearchScreenEvent.ClickHistory -> {
                val chips = event.history.ingredientNames.map{
                    ChipState(it, mutableStateOf(true))
                }
                _state.value = _state.value.copy(
                    chipsForSearch = chips
                )
                viewModelScope.launch {
                    _eventFlow.emit(UiEvent.ChangeChips(chips))
                }
            }
            is SearchScreenEvent.DeleteSearchHistory -> {
                deleteSearchHistory(event.history.id!!)
                    .invokeOnCompletion { getSearchHistories() }
            }
            is SearchScreenEvent.DeleteFavorite -> {
                deleteFavorite(event.favorite.postId!!)
                    .invokeOnCompletion { getFavoritePosts() }
            }
            is SearchScreenEvent.GetSearchHistories -> {
                getSearchHistories()
            }
            is SearchScreenEvent.GetFavoritePosts -> {
                getFavoritePosts()
            }
            is SearchScreenEvent.FavoriteButtonClicked -> {
                if (event.post.isFavorite)
                    unFavoritePost(event.post.id!!, event.idx)
                else
                    favoritePost(event.post.id!!, event.idx)
            }
            is SearchScreenEvent.LikeButtonClicked -> {
                viewModelScope.launch {
                    if(_state.value.currentUser != null) {
                        if (event.post.like)
                            unlikePost(event.post.id!!, event.idx)
                        else
                            likePost(event.post.id!!, event.idx)
                    }else{
                        _eventFlow.emit(UiEvent.ShowToast("좋아요를 표시하려면 로그인 해주세요"))
                    }
                }
            }
        }
    }

    private fun getSearchedPostList() = viewModelScope.launch{
        getPostsByChips().join()
        getFavoritesForFillingStar()
        getPostLikeTags()
        _state.value.posts.forEachIndexed { i, post ->
            getOwnerOfPost(i, post.ownerId!!)
        }
    }

    private fun getFavoritePosts() = viewModelScope.launch {
        getFavorites().join()
        getPostsWithFavorites().join()
        getFavoritePostLikeTags()
        _state.value.favorites.forEachIndexed { index, favorite ->
            getPostOwner(index, favorite.post)
        }
    }

    private fun observeUserChanged() = viewModelScope.launch {
        app.user.collect { user ->
            _state.value = _state.value.copy(
                currentUser = user
            )
            getSearchHistories()
            getFavoritePosts()
        }
    }

    private fun getSearchHistories() = viewModelScope.launch {
        val user = _state.value.currentUser
        useCases.getSearchHistories(user?.account?.id).collect { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        searchHistories = result.data!!,
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
                    _eventFlow.emit(UiEvent.ShowToast("검색 기록을 불러오지 못했습니다(${result.message})"))
                }
            }
        }
    }

    private fun addSearchHistory(searchHistory: SearchHistory) = viewModelScope.launch {
        val login = _state.value.currentUser != null
        useCases.addSearchHistory(searchHistory, login).collect { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
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
                    _eventFlow.emit(UiEvent.ShowToast("검색 기록을 추가하지 못했습니다(${result.message})"))
                }
            }
        }
    }

    private fun deleteSearchHistory(historyId: Long) = viewModelScope.launch {
        val login = _state.value.currentUser != null
        useCases.deleteSearchHistory(historyId, login).collect { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        isLoading = false
                    )
                    _eventFlow.emit(UiEvent.ShowToast("검색 기록이 삭제되었습니다"))
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
                    _eventFlow.emit(UiEvent.ShowToast("검색 기록을 삭제하지 못했습니다 (${result.message})"))
                }
            }
        }
    }

    private fun getFavorites() = viewModelScope.launch {
        val user = _state.value.currentUser
        useCases.getFavorites(user?.account?.id).collect { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        favorites = result.data!!,
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
                    _eventFlow.emit(UiEvent.ShowToast("즐겨찾기 목록을 불러오지 못했습니다 (${result.message})"))
                }
            }
        }
    }

    private fun deleteFavorite(postId: Long) = viewModelScope.launch {
        val ownerId = _state.value.currentUser?.account?.id
        val favoriteId = _state.value.favorites.find{it.postId == postId}?.id
        useCases.deleteFavorite(postId, favoriteId,ownerId != null).collect{ result ->
            when(result){
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        isLoading = false
                    )
                    _eventFlow.emit(UiEvent.ShowToast("즐겨찾기가 삭제되었습니다"))
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
                    _eventFlow.emit(UiEvent.ShowToast("즐겨찾기를 삭제하지 못했습니다 (${result.message})"))
                }
            }
        }
    }

    private fun getPostsWithFavorites() = viewModelScope.launch {
        val postIds = _state.value.favorites.map{it.postId!!}
        useCases.getPost(postIds).collect { result ->
            when (result) {
                is Resource.Success -> {
                    val map = mutableMapOf<Long, Post>()
                    result.data!!.forEach { post ->
                        map[post.id!!] = post
                    }
                    val favorites = _state.value.favorites.toMutableList()
                    for(i in favorites.indices){
                        favorites[i] = favorites[i].copy(
                            post = map[favorites[i].postId]
                        )
                    }
                    _state.value = _state.value.copy(
                        favorites = favorites,
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

    private fun getPostOwner(idx: Int, post: Post?) = viewModelScope.launch {
        val accountId = post?.ownerId!!
        useCases.getOwner(accountId).collect { result ->
            when (result) {
                is Resource.Success -> {
                    val postWithAccount = post?.copy(owner = result.data)
                    val favorites = _state.value.favorites.toMutableList()
                    favorites[idx] = favorites[idx].copy(
                        post = postWithAccount
                    )
                    _state.value = _state.value.copy(
                        favorites = favorites,
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

    private fun getPostsByChips() = viewModelScope.launch {
        val mainIngredients = mutableListOf<String>()
        val subIngredients = mutableListOf<String>()

        _state.value.chipsForSearch?.forEach{ chip ->
            if(chip.isSubIngredient.value) subIngredients.add(chip.text)
            else mainIngredients.add(chip.text)
        }

        useCases.getSearchedPosts(mainIngredients, subIngredients).collect{ result ->
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
                    _eventFlow.emit(UiEvent.ShowToast("검색 결과를 불러오지 못했습니다 (${result.message})"))
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

    private fun getFavoritePostLikeTags() = viewModelScope.launch {
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

                        val favorites = _state.value.favorites.toMutableList()
                        favorites.forEachIndexed { index, favorite ->
                            if (map[favorite.post?.id] != null) {
                                favorites[index] = favorites[index].copy(
                                    post = favorite.post?.copy(
                                        like = true
                                    )
                                )
                            }
                        }

                        _state.value = _state.value.copy(
                            isLoading = false,
                            favorites = favorites
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
                    val favorites = _state.value.favorites.toMutableList()
                    favorites[idx] = favorites[idx].copy(
                        post = favorites[idx].post?.copy(like = true)
                    )
                    val likeTags = _state.value.likeTags.toMutableList()
                    likeTags.add(result.data!!)
                    _state.value = _state.value.copy(
                        likeTags = likeTags,
                        favorites = favorites,
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
        val likeTagId = _state.value.likeTags.find{it.postId == postId}?.id!!
        useCases.unlikePost(likeTagId).collect { result ->
            when (result) {
                is Resource.Success -> {
                    val favorites = _state.value.favorites.toMutableList()
                    favorites[idx] = favorites[idx].copy(
                        post = favorites[idx].post?.copy(like = false)
                    )
                    val likeTags = _state.value.likeTags.toMutableList()
                    likeTags.removeIf { it.id == likeTagId }
                    _state.value = _state.value.copy(
                        likeTags = likeTags,
                        favorites = favorites,
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

    private fun favoritePost(postId: Long, idx: Int) = viewModelScope.launch {
        val ownerId = _state.value.currentUser?.account?.id
        useCases.favoritePost(ownerId, postId, ownerId != null).collect{ result ->
            when(result){
                is Resource.Success -> {
                    val posts = _state.value.posts.toMutableList()
                    val favorites = _state.value.favorites.toMutableList()
                    favorites.removeIf { it.postId == postId }
                    favorites.add(result.data!!)

                    posts[idx] = posts[idx].copy(
                        isFavorite = true
                    )
                    _state.value = _state.value.copy(
                        favorites = favorites,
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

    // call after get Posts
    private fun getOwnerOfPost(postIdx: Int, accountId: Long) = viewModelScope.launch {
        useCases.getOwner(accountId).collect { result ->
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
        object NavigateToSearchResultScreen: UiEvent()
        data class ChangeChips(val chips: List<ChipState>): UiEvent()
    }
}