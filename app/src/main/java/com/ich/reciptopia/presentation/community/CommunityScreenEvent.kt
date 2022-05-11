package com.ich.reciptopia.presentation.community

sealed class CommunityScreenEvent{
    data class CreateBoardStateChanged(val isOn: Boolean): CommunityScreenEvent()
    data class SearchQueryChanged(val query: String): CommunityScreenEvent()
    data class SortOptionChanged(val option: String): CommunityScreenEvent()
    object SearchModeOff: CommunityScreenEvent()
    object SearchButtonClicked: CommunityScreenEvent()

    object GetPosts: CommunityScreenEvent()
    data class GetOwnerAccount(val accountId: Long): CommunityScreenEvent()
}
