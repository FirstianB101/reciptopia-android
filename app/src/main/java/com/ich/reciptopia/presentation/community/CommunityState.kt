package com.ich.reciptopia.presentation.community

import com.ich.reciptopia.domain.model.Favorite
import com.ich.reciptopia.domain.model.Post
import com.ich.reciptopia.domain.model.PostLikeTag
import com.ich.reciptopia.domain.model.User
import com.ich.reciptopia.presentation.main.search.util.ChipState

data class CommunityState(
    val isLoading: Boolean = false,
    val showCreatePostDialog: Boolean = false,
    val searchMode: Boolean = false,
    val searchQuery: String = "",
    val sortOption: String = "최신순",
    val posts: List<Post> = emptyList(),
    val likeTags: List<PostLikeTag> = emptyList(),
    val favorites: List<Favorite> = emptyList(),
    val newPostTitle: String = "",
    val newPostContent: String = "",
    val newPostStep: String = "",
    val newPictureUrls: List<String> = emptyList(),
    val newPostChips: List<ChipState> = emptyList(),
    val showAddChipDialog: Boolean = false,
    val currentUser: User? = null
)
