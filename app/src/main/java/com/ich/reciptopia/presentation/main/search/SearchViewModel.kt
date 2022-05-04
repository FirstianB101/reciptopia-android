package com.ich.reciptopia.presentation.main.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ich.reciptopia.domain.model.SearchHistory
import com.ich.reciptopia.domain.use_case.search_history.SearchHistoryUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchHistoryUseCases: SearchHistoryUseCases
): ViewModel() {
    private val _state = MutableStateFlow(SearchState())
    val state: StateFlow<SearchState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        getSearchHistories()
    }

    fun onEvent(event: SearchScreenEvent){
        when(event){
            is SearchScreenEvent.SearchQueryChanged -> {
                _state.value = _state.value.copy(
                    searchQuery = event.query
                )
            }
            is SearchScreenEvent.SearchModeChanged -> {
                _state.value = _state.value.copy(
                    searchMode = event.isOn
                )
            }
            is SearchScreenEvent.AddSearchHistory -> {
                addSearchHistory(event.history)
            }
            is SearchScreenEvent.DeleteSearchHistory -> {
                deleteSearchHistory(event.history)
            }
            is SearchScreenEvent.ChipTextChanged -> {
                _state.value = _state.value.copy(
                    chipText = event.text
                )
            }
        }
    }

    private fun getSearchHistories(){
        viewModelScope.launch {
            searchHistoryUseCases.getSearchHistories().collect { result ->
                _state.value = _state.value.copy(
                    searchHistories = result
                )
            }
        }
    }

    private fun addSearchHistory(history: SearchHistory){
        viewModelScope.launch {
            searchHistoryUseCases.addSearchHistory(history)
            _eventFlow.emit(UiEvent.ShowToast("검색 기록이 추가되었습니다"))
        }
    }

    private fun deleteSearchHistory(history: SearchHistory){
        viewModelScope.launch {
            searchHistoryUseCases.deleteSearchHistory(history)
            _eventFlow.emit(UiEvent.ShowToast("검색 기록이 삭제되었습니다"))
        }
    }

    sealed class UiEvent{
        data class ShowToast(val message: String): UiEvent()
    }
}