package com.ich.reciptopia.presentation.community

import com.ich.reciptopia.domain.model.Post

sealed class CommunityScreenEvent{
    data class CreatePostStateChanged(val isOn: Boolean): CommunityScreenEvent()
    data class SearchQueryChanged(val query: String): CommunityScreenEvent()
    data class SortOptionChanged(val option: String): CommunityScreenEvent()
    data class CreatePostTitleChanged(val title: String): CommunityScreenEvent()
    data class CreatePostContentChanged(val content: String): CommunityScreenEvent()
    data class CreatePostAddImage(val uri: String): CommunityScreenEvent()
    data class CreatePostRemoveImage(val idx: Int): CommunityScreenEvent()
    data class FavoriteButtonClicked(val post: Post): CommunityScreenEvent()
    object SearchModeOff: CommunityScreenEvent()
    object SearchButtonClicked: CommunityScreenEvent()

    object GetPosts: CommunityScreenEvent()
    object CreatePost: CommunityScreenEvent()
}
