package com.ich.reciptopia.presentation.main

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.ich.reciptopia.common.util.getAddedList
import com.ich.reciptopia.common.util.getRemovedList
import com.ich.reciptopia.presentation.main.search.util.ChipState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(

): ViewModel() {

    private val _state = MutableStateFlow(MainScreenState())
    val state = _state.asStateFlow()

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
                val newChip = ChipState(state.value.searchQuery, mutableStateOf(true))
                _state.value = _state.value.copy(
                    chipStates = _state.value.chipStates.getAddedList(newChip)
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
}