package com.ich.reciptopia.presentation.main

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ich.reciptopia.application.ReciptopiaApplication
import com.ich.reciptopia.common.util.getAddedList
import com.ich.reciptopia.common.util.getRemovedList
import com.ich.reciptopia.presentation.main.search.util.ChipState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val app: ReciptopiaApplication
): ViewModel() {

    private val _state = MutableStateFlow(MainScreenState())
    val state = _state.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        observeUserChanged()
    }

    fun onEvent(event: MainScreenEvent){
        when(event){
            is MainScreenEvent.SearchModeChanged -> {
                _state.value = _state.value.copy(
                    searchMode = event.isOn
                )
            }
            is MainScreenEvent.SearchQueryChanged -> {
                _state.value = _state.value.copy(
                    searchQuery = event.query
                )
            }
            is MainScreenEvent.OnChipClicked -> {
                val previous = _state.value.chipStates[event.idx].isSubIngredient.value
                _state.value.chipStates[event.idx].isSubIngredient.value = !previous
            }
            is MainScreenEvent.AddChip -> {
                if(_state.value.searchQuery.isNotBlank()) {
                    val newChip = ChipState(_state.value.searchQuery, mutableStateOf(true))
                    _state.value = _state.value.copy(
                        chipStates = _state.value.chipStates.getAddedList(newChip)
                    )
                }else{
                    viewModelScope.launch {
                        _eventFlow.emit(UiEvent.ShowToast("재료 이름을 입력 후 추가해주세요"))
                    }
                }
            }
            is MainScreenEvent.AddChips -> {
                _state.value = _state.value.copy(
                    chipStates = event.chips
                )
            }
            is MainScreenEvent.RemoveChip -> {
                _state.value = _state.value.copy(
                    chipStates = _state.value.chipStates.getRemovedList(event.chip)
                )
            }
            is MainScreenEvent.ResetChips -> {
                _state.value = _state.value.copy(
                    chipStates = emptyList()
                )
            }
            is MainScreenEvent.SetChipsFromAnalyze -> {
                _state.value = _state.value.copy(
                    chipStates = event.chips
                )
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

    sealed class UiEvent{
        data class ShowToast(val message: String): UiEvent()
    }
}