package com.ich.reciptopia.presentation.main

import com.ich.reciptopia.presentation.main.search.util.ChipState

sealed class MainScreenEvent{
    data class SearchModeChanged(val isOn: Boolean): MainScreenEvent()
    data class SearchQueryChanged(val query: String): MainScreenEvent()
    data class OnChipClicked(val idx: Int): MainScreenEvent()
    object AddChip: MainScreenEvent()
    data class AddChips(val chips: List<ChipState>): MainScreenEvent()
    data class RemoveChip(val chip: ChipState): MainScreenEvent()
    data class SetChipsFromAnalyze(val chips: List<ChipState>): MainScreenEvent()
    object ResetChips: MainScreenEvent()
}
