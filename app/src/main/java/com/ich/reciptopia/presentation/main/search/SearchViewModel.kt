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
                addSearchHistoryInDB(newHistory)
            }
            is SearchScreenEvent.DeleteSearchHistory -> {
                deleteSearchHistoryFromDB(event.history)
            }
            is SearchScreenEvent.DeleteFavorite -> {
                deleteFavoriteFromDB(event.favorite)
                    .invokeOnCompletion { getFavoritePosts() }
            }
            is SearchScreenEvent.GetSearchHistories -> {
                getSearchHistoriesFromDB()
            }
            is SearchScreenEvent.GetFavoritePosts -> {
                getFavoritePosts()
            }
        }
    }

    private fun getFavoritePosts(){
        getFavoritesFromDB().invokeOnCompletion {
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
        }
    }

    private fun getSearchHistoriesFromDB() = viewModelScope.launch {
        useCases.getSearchHistoriesFromDB().collect { result ->
            _state.value = _state.value.copy(
                searchHistories = result
            )
        }
    }

    private fun addSearchHistoryInDB(history: SearchHistory) = viewModelScope.launch {
        useCases.addSearchHistoryInDB(history)
        _eventFlow.emit(UiEvent.ShowToast("검색 기록이 추가되었습니다"))
    }


    private fun deleteSearchHistoryFromDB(history: SearchHistory) = viewModelScope.launch {
        useCases.deleteSearchHistoryFromDB(history)
        _eventFlow.emit(UiEvent.ShowToast("검색 기록이 삭제되었습니다"))
    }

    private fun getFavoritesFromDB() = viewModelScope.launch {
        _state.value = _state.value.copy(
            favorites = useCases.getFavoritesFromDB().first()
        )
    }

    private fun deleteFavoriteFromDB(favorite: Favorite) = viewModelScope.launch {
        useCases.deleteFavoriteFromDB(favorite)
        _eventFlow.emit(UiEvent.ShowToast("즐겨찾기가 삭제되었습니다"))
    }

    private fun getSearchHistories() = viewModelScope.launch {
        val userAccount = _state.value.currentUser?.account
        if (userAccount != null) {
            useCases.getSearchHistories(userAccount.id!!).collect { result ->
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
    }

    private fun addSearchHistory(searchHistory: SearchHistory) = viewModelScope.launch {
        useCases.addSearchHistory(searchHistory).collect { result ->
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
        useCases.deleteSearchHistory(historyId).collect { result ->
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
            when(result){
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