package com.ich.reciptopia.presentation.community

data class CommunityState(
    val showCreateBoardDialog: Boolean = false,
    val searchMode: Boolean = false,
    val searchQuery: String = "",
)
