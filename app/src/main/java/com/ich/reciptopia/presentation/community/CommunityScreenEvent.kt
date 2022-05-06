package com.ich.reciptopia.presentation.community

sealed class CommunityScreenEvent{
    data class CreateBoardStateChanged(val isOn: Boolean): CommunityScreenEvent()
    data class SearchQueryChanged(val query: String): CommunityScreenEvent()
    object SearchModeOff: CommunityScreenEvent()
    object SearchButtonClicked: CommunityScreenEvent()
}
