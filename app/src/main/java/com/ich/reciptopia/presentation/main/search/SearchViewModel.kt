package com.ich.reciptopia.presentation.main.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ich.reciptopia.domain.model.SearchHistory
import com.ich.reciptopia.domain.use_case.search_history.SearchHistoryUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchHistoryUseCases: SearchHistoryUseCases
): ViewModel() {
    private val _state = MutableStateFlow<SearchState>(SearchState.Normal)
    val state: StateFlow<SearchState> = _state

    init {
        getSearchHistories()
    }

    fun getSearchHistories(){
        viewModelScope.launch {
            searchHistoryUseCases.getSearchHistories().collect { result ->
                _state.value = SearchState.GetSearchHistory(result)
            }
        }
    }

    fun addSearchHistory(history: SearchHistory){
        viewModelScope.launch {
            searchHistoryUseCases.addSearchHistory(history)
            _state.value = SearchState.AddSearchHistory
        }
    }

    fun deleteSearchHistory(history: SearchHistory){
        viewModelScope.launch {
            searchHistoryUseCases.deleteSearchHistory(history)
            _state.value = SearchState.DeleteSearchHistory
        }
    }
}