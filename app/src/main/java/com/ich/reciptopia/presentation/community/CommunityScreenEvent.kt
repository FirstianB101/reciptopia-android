package com.ich.reciptopia.presentation.community

import com.ich.reciptopia.domain.model.Post
import com.ich.reciptopia.domain.model.Step
import com.ich.reciptopia.presentation.main.search.util.ChipState

sealed class CommunityScreenEvent{
    data class CreatePostStateChanged(val isOn: Boolean): CommunityScreenEvent()
    data class SearchQueryChanged(val query: String): CommunityScreenEvent()
    data class SortOptionChanged(val option: String): CommunityScreenEvent()

    data class CreatePostTitleChanged(val title: String): CommunityScreenEvent()
    data class CreatePostContentChanged(val content: String): CommunityScreenEvent()
    data class CreatePostAddImage(val uri: String): CommunityScreenEvent()
    data class CreatePostRemoveImage(val idx: Int): CommunityScreenEvent()
    data class AddChips(val chips: List<ChipState>): CommunityScreenEvent()
    data class RemoveChip(val idx: Int): CommunityScreenEvent()
    data class AddChipDialogStateChanged(val show: Boolean): CommunityScreenEvent()
    data class ClickChip(val idx: Int): CommunityScreenEvent()
    data class AddSteps(val steps: List<Step>): CommunityScreenEvent()
    data class StepDialogStateChanged(val show: Boolean): CommunityScreenEvent()

    data class FavoriteButtonClicked(val post: Post, val idx: Int): CommunityScreenEvent()
    data class LikeButtonClicked(val post: Post, val idx: Int): CommunityScreenEvent()
    object SearchModeOff: CommunityScreenEvent()
    object SearchButtonClicked: CommunityScreenEvent()

    object SearchPosts: CommunityScreenEvent()
    object CreatePost: CommunityScreenEvent()
}
