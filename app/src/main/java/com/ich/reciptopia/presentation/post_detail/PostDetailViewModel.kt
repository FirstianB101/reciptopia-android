package com.ich.reciptopia.presentation.post_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ich.reciptopia.application.ReciptopiaApplication
import com.ich.reciptopia.common.util.Resource
import com.ich.reciptopia.domain.model.Comment
import com.ich.reciptopia.domain.model.PostLikeTag
import com.ich.reciptopia.domain.use_case.post_detail.PostDetailUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostDetailViewModel @Inject constructor(
    private val useCases: PostDetailUseCases,
    private val app: ReciptopiaApplication
): ViewModel() {

    private val _state = MutableStateFlow(PostDetailState())
    val state = _state.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var postId = -1L

    fun initialize(postId: Long){
        this.postId = postId
        observeUserChanged()
        fillCurPostInfo()
    }

    fun onEvent(event: PostDetailEvent){
        when(event){
            is PostDetailEvent.ClickLike -> {
                val isLogin = _state.value.currentUser != null
                val isLike = _state.value.curPost?.like == true
                if(isLogin){
                    if(isLike)
                        unlikePost()
                    else
                        likePost()
                }else{
                    viewModelScope.launch {
                        _eventFlow.emit(UiEvent.ShowToast("로그인 후 좋아요를 누를 수 있습니다"))
                    }
                }
            }
            is PostDetailEvent.ClickFavorite -> {
                viewModelScope.launch {
                    val post = _state.value.curPost!!
                    if(post.id != null) {
                        if (post.isFavorite) {
                            unFavoritePost(post.id).join()
                            getPostInfo().join()
                            fillCurPostInfo()
                        } else {
                            favoritePost(post.id).join()
                            getPostInfo().join()
                            fillCurPostInfo()
                        }
                    }
                }
            }
            is PostDetailEvent.CommentTextChanged -> {
                _state.value = _state.value.copy(
                    commentText = event.text
                )
            }
            is PostDetailEvent.CreateComment -> {
                viewModelScope.launch {
                    val isLogin = _state.value.currentUser != null
                    if(isLogin){
                        createComment().join()
                        getCommentsWithReply()
                    }else{
                        _eventFlow.emit(UiEvent.ShowToast("로그인이 필요합니다"))
                    }
                }
            }
            is PostDetailEvent.CommentLikeButtonClick -> {
                viewModelScope.launch {
                    val isLogin = _state.value.currentUser != null
                    if(isLogin){
                        if(event.comment.like)
                            unlikeComment(event.comment.id!!, event.idx)
                        else
                            likeComment(event.comment.id!!, event.idx)
                    }else{
                        _eventFlow.emit(UiEvent.ShowToast("로그인이 필요합니다"))
                    }
                }
            }
            is PostDetailEvent.ReplyLikeButtonClick -> {
                viewModelScope.launch {
                    val isLogin = _state.value.currentUser != null
                    if(isLogin){
                        if(event.reply.like)
                            unlikeReply(event.reply.id!!, event.commentIdx, event.replyIdx)
                        else
                            likeReply(event.reply.id!!, event.commentIdx, event.replyIdx)

                    }else{
                        _eventFlow.emit(UiEvent.ShowToast("로그인이 필요합니다"))
                    }
                }
            }
        }
    }

    private fun observeUserChanged() = viewModelScope.launch {
        app.user.collect{ user ->
            _state.value = _state.value.copy(
                currentUser = user
            )
            fillCurPostInfo()
        }
    }

    private fun fillCurPostInfo() = viewModelScope.launch{
        getPostInfo().join()
        getOwnerById()
        getFavoritePostsForFillingStar()
        getPostLikeTagsForFillingThumb()
        getRecipe(postId).join()
        getMainIngredients(postId)
        getSubIngredients(postId)
        getSteps(_state.value.curRecipe?.id!!)
        getCommentsWithReply()
    }

    private suspend fun getCommentsWithReply(){
        getComments(postId).join()
        _state.value.comments.forEachIndexed { cIdx, comment ->
            getOwnerOfComment(comment.ownerId!!,cIdx)
            getReplies(comment.id!!, cIdx).join()
        }
        _state.value.comments.forEachIndexed { cIdx, comment ->
            comment.replies?.forEachIndexed { rIdx, reply ->
                getOwnerOfReply(reply.ownerId!!, cIdx, rIdx)
            }
        }
        getCommentLikeTags()
        getReplyLikeTags()
    }

    private fun getPostInfo() = viewModelScope.launch{
        useCases.getPostInfo(postId).collect{ result ->
            when(result){
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        curPost = result.data!!,
                        isLoading = false
                    )
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(
                        isLoading = true
                    )
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false
                    )
                    _eventFlow.emit(UiEvent.ShowToast("포스트 정보를 불러오지 못했습니다 (${result.message})"))
                }
            }
        }
    }

    // call after get current post
    private fun getOwnerById() = viewModelScope.launch {
        useCases.getOwnerById(_state.value.curPost?.ownerId!!).collect{ result ->
            if(result is Resource.Success){
                val post = _state.value.curPost?.copy(
                    owner = result.data
                )
                _state.value = _state.value.copy(
                    curPost = post
                )
            }
        }
    }

    private fun favoritePost(postId: Long) = viewModelScope.launch {
        val userId = _state.value.currentUser?.account?.id
        useCases.favoritePost(userId, postId, userId != null).collect{ result ->
            when(result){
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        isLoading = false
                    )
                    _eventFlow.emit(UiEvent.ShowToast("즐겨찾기에 추가되었습니다"))
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(
                        isLoading = true
                    )
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false
                    )
                    _eventFlow.emit(UiEvent.ShowToast("즐겨찾기에 추가하지 못했습니다 (${result.message})"))
                }
            }
        }
    }

    private fun unFavoritePost(postId: Long) = viewModelScope.launch {
        val userId = _state.value.currentUser?.account?.id
        useCases.unFavoritePost(postId, 0L, userId != null).collect{ result ->
            when(result){
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        isLoading = false
                    )
                    _eventFlow.emit(UiEvent.ShowToast("즐겨찾기가 제거되었습니다"))
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(
                        isLoading = true
                    )
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false
                    )
                    _eventFlow.emit(UiEvent.ShowToast("즐겨찾기를 제거하지 못했습니다 (${result.message})"))
                }
            }
        }
    }

    private fun getFavoritePostsForFillingStar() = viewModelScope.launch {
        val userId = _state.value.currentUser?.account?.id
        useCases.getFavorites(userId).collect { result ->
            when(result){
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        isLoading = false
                    )

                    for(favorite in result.data!!){
                        if(favorite.postId == _state.value.curPost?.id){
                            val post = _state.value.curPost?.copy(
                                isFavorite = true
                            )
                            _state.value = _state.value.copy(
                                curPostFavorite = favorite,
                                curPost = post
                            )
                            break
                        }
                    }
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(
                        isLoading = true
                    )
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false
                    )
                }
            }
        }
    }

    private fun likePost() = viewModelScope.launch {
        val userId = _state.value.currentUser?.account?.id!!
        useCases.likePost(userId, postId).collect { result ->
            when (result) {
                is Resource.Success -> {
                    val post = _state.value.curPost
                    _state.value = _state.value.copy(
                        curPost = post?.copy(like = true),
                        isLoading = false
                    )
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(
                        isLoading = true
                    )
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false
                    )
                    _eventFlow.emit(UiEvent.ShowToast("좋아요를 표시하지 못했습니다(${result.message})"))
                }
            }
        }
    }

    private fun unlikePost() = viewModelScope.launch {
        val likeTagId = _state.value.curPostLikeTag?.id
        useCases.unlikePost(likeTagId).collect { result ->
            when (result) {
                is Resource.Success -> {
                    val post = _state.value.curPost
                    _state.value = _state.value.copy(
                        curPost = post?.copy(like = false),
                        isLoading = false
                    )
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(
                        isLoading = true
                    )
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false
                    )
                    _eventFlow.emit(UiEvent.ShowToast("좋아요를 취소하지 못했습니다(${result.message})"))
                }
            }
        }
    }

    private fun getPostLikeTagsForFillingThumb() = viewModelScope.launch {
        val userId = _state.value.currentUser?.account?.id
        if(userId != null) {
            useCases.getLikeTags(userId).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        val tags = result.data!!
                        val post = _state.value.curPost
                        var like = false
                        var likeTag: PostLikeTag? = null

                        tags.forEach {
                            if (it.ownerId == userId && it.postId == post?.id) {
                                like = true
                                likeTag = it
                            }
                        }

                        _state.value = _state.value.copy(
                            curPostLikeTag = likeTag,
                            curPost = post?.copy(like = like),
                            isLoading = false
                        )
                    }
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(
                            isLoading = true
                        )
                    }
                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            isLoading = false
                        )
                    }
                }
            }
        }
    }

    private fun getRecipe(postId: Long) = viewModelScope.launch {
        useCases.getRecipe(postId).collect{ result ->
            when(result){
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        curRecipe = result.data,
                        isLoading = false
                    )
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(
                        isLoading = true
                    )
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false
                    )
                }
            }
        }
    }

    private fun getMainIngredients(postId: Long) = viewModelScope.launch {
        useCases.getMainIngredients(postId).collect { result ->
            when(result){
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        mainIngredients = result.data!!,
                        isLoading = false
                    )
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(
                        isLoading = true
                    )
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false
                    )
                }
            }
        }
    }

    private fun getSubIngredients(postId: Long) = viewModelScope.launch {
        useCases.getSubIngredients(postId).collect { result ->
            when(result){
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        subIngredients = result.data!!,
                        isLoading = false
                    )
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(
                        isLoading = true
                    )
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false
                    )
                }
            }
        }
    }

    private fun getSteps(recipeId: Long) = viewModelScope.launch {
        useCases.getSteps(recipeId).collect { result ->
            when(result){
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        curPostSteps = result.data!!,
                        isLoading = false
                    )
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(
                        isLoading = true
                    )
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false
                    )
                }
            }
        }
    }

    private fun getComments(postId: Long) = viewModelScope.launch {
        useCases.getComments(postId).collect { result ->
            when(result){
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        comments = result.data!!,
                        isLoading = false
                    )
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(
                        isLoading = true
                    )
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false
                    )
                }
            }
        }
    }

    private fun getReplies(commentId: Long, idx: Int) = viewModelScope.launch {
        useCases.getReplies(commentId).collect { result ->
            when(result){
                is Resource.Success -> {
                    val comments = _state.value.comments.toMutableList()
                    comments[idx] = comments[idx].copy(
                        replies = result.data
                    )
                    _state.value = _state.value.copy(
                        comments = comments,
                        isLoading = false
                    )
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(
                        isLoading = true
                    )
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false
                    )
                }
            }
        }
    }

    private fun getOwnerOfComment(ownerId: Long, idx: Int) = viewModelScope.launch {
        useCases.getOwnerById(ownerId).collect { result ->
            when(result){
                is Resource.Success -> {
                    val comments = _state.value.comments.toMutableList()
                    comments[idx] = comments[idx].copy(
                        owner = result.data
                    )
                    _state.value = _state.value.copy(
                        comments = comments,
                        isLoading = false
                    )
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(
                        isLoading = true
                    )
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false
                    )
                }
            }
        }
    }

    private fun getOwnerOfReply(ownerId: Long, commentIdx: Int, replyIdx: Int) = viewModelScope.launch {
        useCases.getOwnerById(ownerId).collect { result ->
            when(result){
                is Resource.Success -> {
                    val comments = _state.value.comments.toMutableList()
                    val replies = comments[commentIdx].replies?.toMutableList()
                    replies?.set(replyIdx, replies[replyIdx].copy(owner = result.data!!))
                    comments[commentIdx] = comments[commentIdx].copy(
                        replies = replies
                    )
                    _state.value = _state.value.copy(
                        comments = comments,
                        isLoading = false
                    )
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(
                        isLoading = true
                    )
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false
                    )
                }
            }
        }
    }

    private fun createComment() = viewModelScope.launch {
        val newComment = Comment(
            ownerId = _state.value.currentUser?.account?.id,
            postId = postId,
            content = _state.value.commentText,
        )
        useCases.createComment(newComment).collect { result ->
            when(result){
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        commentText = "",
                        isLoading = false
                    )
                    _eventFlow.emit(UiEvent.ShowToast("댓글을 작성하였습니다"))
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(
                        isLoading = true
                    )
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false
                    )
                    _eventFlow.emit(UiEvent.ShowToast("댓글을 작성하지 못했습니다 (${result.message})"))
                }
            }
        }
    }

    private fun getCommentLikeTags() = viewModelScope.launch {
        val ownerId = _state.value.currentUser?.account?.id
        useCases.getCommentLikeTags(ownerId).collect { result ->
            when(result){
                is Resource.Success -> {
                    val comments = _state.value.comments.toMutableList()
                    val map = mutableMapOf<Long, Boolean>()

                    val tags = result.data!!
                    tags.forEach { commentLikeTag ->
                        map[commentLikeTag.commentId!!] = true
                    }

                    for(i in comments.indices){
                        if(map[comments[i].id] == true)
                            comments[i] = comments[i].copy(like = true)
                    }
                    _state.value = _state.value.copy(
                        comments = comments,
                        commentLikeTags = tags,
                        isLoading = false
                    )
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(
                        isLoading = true
                    )
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false
                    )
                }
            }
        }
    }

    private fun getReplyLikeTags() = viewModelScope.launch {
        val ownerId = _state.value.currentUser?.account?.id
        useCases.getReplyLikeTags(ownerId).collect { result ->
            when(result){
                is Resource.Success -> {
                    val comments = _state.value.comments.toMutableList()
                    val map = mutableMapOf<Long, Boolean>()

                    val tags = result.data!!
                    tags.forEach { replyLikeTag ->
                        map[replyLikeTag.replyId!!] = true
                    }

                    for(i in comments.indices){
                        val replies = comments[i].replies?.toMutableList()
                        for(j in replies!!.indices){
                            if(map[replies[j].id] == true)
                                replies[j] = replies[j].copy(like = true)
                        }
                        comments[i] = comments[i].copy(replies = replies)
                    }
                    _state.value = _state.value.copy(
                        comments = comments,
                        replyLikeTags = tags,
                        isLoading = false
                    )
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(
                        isLoading = true
                    )
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false
                    )
                }
            }
        }
    }

    private fun likeComment(commentId: Long, idx: Int) = viewModelScope.launch {
        val ownerId = _state.value.currentUser?.account?.id
        useCases.likeComment(ownerId, commentId).collect { result ->
            when(result){
                is Resource.Success -> {
                    val comments = _state.value.comments.toMutableList()
                    comments[idx] = comments[idx].copy(like = true)
                    _state.value = _state.value.copy(
                        comments = comments,
                        isLoading = false
                    )
                    _eventFlow.emit(UiEvent.ShowToast("댓글에 좋아요를 눌렀습니다"))
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(
                        isLoading = true
                    )
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false
                    )
                    _eventFlow.emit(UiEvent.ShowToast("좋아요를 누르지 못했습니다 (${result.message})"))
                }
            }
        }
    }

    private fun likeReply(replyId: Long, cIdx: Int, rIdx: Int) = viewModelScope.launch {
        val ownerId = _state.value.currentUser?.account?.id
        useCases.likeReply(ownerId, replyId).collect { result ->
            when(result){
                is Resource.Success -> {
                    val comments = _state.value.comments.toMutableList()
                    val replies = comments[cIdx].replies!!.toMutableList()

                    replies[rIdx] = replies[rIdx].copy(like = true)
                    comments[cIdx] = comments[cIdx].copy(replies = replies)
                    _state.value = _state.value.copy(
                        comments = comments,
                        isLoading = false
                    )
                    _eventFlow.emit(UiEvent.ShowToast("댓글에 좋아요를 눌렀습니다"))
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(
                        isLoading = true
                    )
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false
                    )
                    _eventFlow.emit(UiEvent.ShowToast("좋아요를 누르지 못했습니다 (${result.message})"))
                }
            }
        }
    }

    private fun unlikeComment(commentId: Long, idx: Int) = viewModelScope.launch {
        val tagId = _state.value.commentLikeTags.find{ it.commentId == commentId }?.id
        useCases.unlikeComment(tagId).collect { result ->
            when(result){
                is Resource.Success -> {
                    val comments = _state.value.comments.toMutableList()
                    comments[idx] = comments[idx].copy(like = false)
                    _state.value = _state.value.copy(
                        comments = comments,
                        isLoading = false
                    )
                    _eventFlow.emit(UiEvent.ShowToast("좋아요를 취소했습니다"))
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(
                        isLoading = true
                    )
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false
                    )
                    _eventFlow.emit(UiEvent.ShowToast("좋아요를 취소하지 못했습니다 (${result.message})"))
                }
            }
        }
    }

    private fun unlikeReply(replyId: Long, cIdx: Int, rIdx: Int) = viewModelScope.launch {
        val tagId = _state.value.replyLikeTags.find{ it.replyId == replyId }?.id
        useCases.unlikeReply(tagId).collect { result ->
            when(result){
                is Resource.Success -> {
                    val comments = _state.value.comments.toMutableList()
                    val replies = comments[cIdx].replies!!.toMutableList()

                    replies[rIdx] = replies[rIdx].copy(like = false)
                    comments[cIdx] = comments[cIdx].copy(replies = replies)

                    _state.value = _state.value.copy(
                        comments = comments,
                        isLoading = false
                    )
                    _eventFlow.emit(UiEvent.ShowToast("좋아요를 취소했습니다"))
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(
                        isLoading = true
                    )
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false
                    )
                    _eventFlow.emit(UiEvent.ShowToast("좋아요를 취소하지 못했습니다 (${result.message})"))
                }
            }
        }
    }

    sealed class UiEvent{
        data class ShowToast(val message: String): UiEvent()
    }
}