package com.ich.reciptopia.presentation.main.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ich.reciptopia.application.ReciptopiaApplication
import com.ich.reciptopia.domain.model.FavoriteEntity
import com.ich.reciptopia.domain.model.SearchHistoryEntity
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
        getSearchHistories()
        getFavoriteEntities()
    }

    fun onEvent(event: SearchScreenEvent) {
        when (event) {
            is SearchScreenEvent.AddSearchHistoryEntity -> {
                addSearchHistory(event.historyEntity)
            }
            is SearchScreenEvent.DeleteSearchHistoryEntity -> {
                deleteSearchHistory(event.historyEntity)
            }
            is SearchScreenEvent.DeleteFavoriteEntity -> {
                deleteFavoriteEntity(event.favoriteEntity)
            }
        }
    }

    private fun observeUserChanged() = viewModelScope.launch {
        app.user.collect{ user ->
            _state.value = _state.value.copy(
                currentUser = user
            )
        }
    }

    private fun getSearchHistories() = viewModelScope.launch {
        useCases.getSearchHistoryEntities().collect { result ->
            _state.value = _state.value.copy(
                searchHistoryEntities = result
            )
        }
    }

    private fun addSearchHistory(historyEntity: SearchHistoryEntity) = viewModelScope.launch {
        useCases.addSearchHistoryEntity(historyEntity)
        _eventFlow.emit(UiEvent.ShowToast("검색 기록이 추가되었습니다"))
    }


    private fun deleteSearchHistory(historyEntity: SearchHistoryEntity) = viewModelScope.launch {
        useCases.deleteSearchHistoryEntity(historyEntity)
        _eventFlow.emit(UiEvent.ShowToast("검색 기록이 삭제되었습니다"))
    }


    private fun getFavoriteEntities() = viewModelScope.launch{
        useCases.getFavoriteEntities().collect{ result ->
            _state.value = _state.value.copy(
                favoriteEntities = result
            )
        }
    }

    private fun deleteFavoriteEntity(favoriteEntity: FavoriteEntity) = viewModelScope.launch {
        useCases.deleteFavoriteEntity(favoriteEntity)
        _eventFlow.emit(UiEvent.ShowToast("즐겨찾기가 삭제되었습니다"))
    }

    sealed class UiEvent {
        data class ShowToast(val message: String) : UiEvent()
    }
}