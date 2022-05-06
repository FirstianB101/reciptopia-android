package com.ich.reciptopia.presentation.main

import com.ich.reciptopia.presentation.main.search.util.ChipState

data class MainScreenState(
    val chipStates: List<ChipState> = emptyList(),
    val searchMode: Boolean = false,
    val searchQuery: String = ""
)
