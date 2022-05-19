package com.ich.reciptopia.presentation.main.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ich.reciptopia.application.ReciptopiaApplication
import com.ich.reciptopia.common.util.Resource
import com.ich.reciptopia.domain.model.Favorite
import com.ich.reciptopia.domain.model.Post
import com.ich.reciptopia.domain.model.SearchHistory
import com.ich.reciptopia.domain.use_case.search.SearchUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
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
            is SearchScreenEvent.AddSearchHistory -> {
                val newHistory = SearchHistory(
                    ownerId = _state.value.currentUser?.account?.id,
                    ingredientNames = event.ingredientNames
                )
                addSearchHistory(newHistory)
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
        }
    }

    private fun getFavoritePosts() {
        getFavorites().invokeOnCompletion {
            _state.value.favorites.forEachIndexed { index, favorite ->
                getPostsFromFavoritesFromDB(index, favorite)
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
        val login = _state.value.currentUser != null
        useCases.deleteFavorite(postId, login).collect{ result ->
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

    private fun getPostsFromFavoritesFromDB(idx: Int, favorite: Favorite) = viewModelScope.launch {
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

    sealed class UiEvent {
        data class ShowToast(val message: String) : UiEvent()
    }
}