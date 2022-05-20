package com.ich.reciptopia.presentation.main.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ich.reciptopia.application.ReciptopiaApplication
import com.ich.reciptopia.common.util.Resource
import com.ich.reciptopia.domain.model.Favorite
import com.ich.reciptopia.domain.model.Post
import com.ich.reciptopia.domain.model.PostLikeTag
import com.ich.reciptopia.domain.model.SearchHistory
import com.ich.reciptopia.domain.use_case.search.SearchUseCases
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

    init {
        observeUserChanged()
    }

    fun onEvent(event: SearchScreenEvent) {
        when (event) {
            is SearchScreenEvent.DoSearch -> {
                getSearchedPostList()
                val newHistory = SearchHistory(
                    ownerId = _state.value.currentUser?.account?.id,
                    ingredientNames = event.ingredientNames
                )
                addSearchHistory(newHistory)
                    .invokeOnCompletion { getSearchHistories() }

                viewModelScope.launch {
                    _eventFlow.emit(UiEvent.NavigateToSearchResultScreen)
                }
            }
            is SearchScreenEvent.ClickHistory -> {
                getSearchedPostList()

                viewModelScope.launch {
                    _eventFlow.emit(UiEvent.NavigateToSearchResultScreen)
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
                if (event.post.isFavorite) {
                    unFavoritePost(event.post.id!!)
                        .invokeOnCompletion { getSearchedPostList() }
                } else {
                    favoritePost(event.post.id!!)
                        .invokeOnCompletion { getSearchedPostList() }
                }
            }
            is SearchScreenEvent.LikeButtonClicked -> {
                if(_state.value.currentUser != null) {
                    if (event.post.like) {
                        unlikePost(event.post.id!!)
                            .invokeOnCompletion { getSearchedPostList() }
                    } else {
                        likePost(event.post.id!!)
                            .invokeOnCompletion { getSearchedPostList() }
                    }
                }else{
                    viewModelScope.launch {
                        _eventFlow.emit(UiEvent.ShowToast("좋아요를 표시하려면 로그인 해주세요"))
                    }
                }
            }
        }
    }

    private fun getSearchedPostList(){
        getSearchedPosts().invokeOnCompletion {
            getFavoritesForFillingStar()
            getPostLikeTags()
            _state.value.posts.forEachIndexed { i, post ->
                getOwnerOfPost(i, post.ownerId!!)
            }
        }
    }

    private fun getFavoritePosts() = viewModelScope.launch {
        getFavorites().invokeOnCompletion {
            _state.value.favorites.forEachIndexed { index, favorite ->
                getPostsWithFavorites(index, favorite)
                    .invokeOnCompletion {
                        getPostOwner(index, _state.value.favorites[index].post)
                    }
            }
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
        useCases.deleteFavorite(ownerId, postId, ownerId != null).collect{ result ->
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

    private fun getPostsWithFavorites(idx: Int, favorite: Favorite) = viewModelScope.launch {
        val postId = _state.value.favorites[idx].postId!!
        useCases.getPost(postId).collect { result ->
            when (result) {
                is Resource.Success -> {
                    val favorites = _state.value.favorites.toMutableList()
                    favorites[idx] = favorite.copy(
                        post = result.data
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

    private fun getPostOwner(idx: Int, post: Post?) = viewModelScope.launch {
        val accountId = post?.ownerId!!
        useCases.getOwner(accountId).collect { result ->
            when (result) {
                is Resource.Success -> {
                    val postWithAccount = post.copy(owner = result.data)
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

    private fun getSearchedPosts() = viewModelScope.launch {
        useCases.getSearchedPosts().collect{ result ->
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

    private fun likePost(postId: Long) = viewModelScope.launch {
        val userId = _state.value.currentUser?.account?.id!!
        useCases.likePost(userId, postId).collect { result ->
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
                    _eventFlow.emit(UiEvent.ShowToast("좋아요를 표시하지 못했습니다(${result.message})"))
                }
            }
        }
    }

    private fun unlikePost(postId: Long) = viewModelScope.launch {
        val userId = _state.value.currentUser?.account?.id!!
        useCases.unlikePost(userId, postId).collect { result ->
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
                    _eventFlow.emit(UiEvent.ShowToast("좋아요를 취소하지 못했습니다(${result.message})"))
                }
            }
        }
    }

    private fun favoritePost(postId: Long) = viewModelScope.launch {
        val ownerId = _state.value.currentUser?.account?.id
        useCases.favoritePost(ownerId, postId, ownerId != null).collect{ result ->
            when(result){
                is Resource.Success -> {
                    _state.value = _state.value.copy(
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

    private fun unFavoritePost(postId: Long) = viewModelScope.launch {
        val ownerId = _state.value.currentUser?.account?.id
        useCases.unFavoritePost(ownerId, postId, ownerId != null).collect{ result ->
            when(result){
                is Resource.Success -> {
                    _state.value = _state.value.copy(
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
    }
}