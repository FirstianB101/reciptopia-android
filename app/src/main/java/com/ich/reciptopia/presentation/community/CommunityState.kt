package com.ich.reciptopia.presentation.community

import com.ich.reciptopia.domain.model.Account
import com.ich.reciptopia.domain.model.Post
import com.ich.reciptopia.domain.model.PostLikeTag

data class CommunityState(
    val isLoading: Boolean = false,
    val showCreatePostDialog: Boolean = false,
    val searchMode: Boolean = false,
    val searchQuery: String = "",
    val sortOption: String = "최신순",
    val posts: List<Post> = emptyList(),
    val likeTags: List<PostLikeTag> = emptyList(),
    val like: List<Boolean> = emptyList(),
    val postOwner: Account? = null
)
