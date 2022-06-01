package com.ich.reciptopia.presentation.post_detail.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ich.reciptopia.application.ReciptopiaApplication
import com.ich.reciptopia.common.util.Resource
import com.ich.reciptopia.domain.model.Comment
import com.ich.reciptopia.domain.model.Reply
import com.ich.reciptopia.domain.use_case.post_detail.chat.PostDetailChatUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostDetailChatViewModel @Inject constructor(
    private val useCases: PostDetailChatUseCases,
    private val app: ReciptopiaApplication
): ViewModel() {

    private val _state = MutableStateFlow(PostDetailChatState())
    val state = _state.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var postId = -1L

    fun initialize(postId: Long){
        this.postId = postId
        observeUserChanged()
        getCommentsWithReply()
    }

    fun onEvent(event: PostDetailChatEvent){
        when(event){
            is PostDetailChatEvent.CommentTextChanged -> {
                _state.value = _state.value.copy(
                    inputText = event.text
                )
            }
            is PostDetailChatEvent.CreateCommentOrReply -> {
                viewModelScope.launch {
                    val isLogin = _state.value.currentUser != null
                    val createReply = _state.value.selectedCommentIdx != null
                    if(isLogin){
                        if(createReply) createReply().join()
                        else createComment().join()
                        getCommentsWithReply()
                    }else{
                        _eventFlow.emit(UiEvent.ShowToast("로그인이 필요합니다"))
                    }
                }
            }
            is PostDetailChatEvent.CommentLikeButtonClick -> {
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
            is PostDetailChatEvent.SelectComment -> {
                val existingIdx = _state.value.selectedCommentIdx
                val selectedIdx = if(existingIdx == event.idx) null else event.idx
                _state.value = _state.value.copy(
                    selectedCommentIdx = selectedIdx
                )
            }
            is PostDetailChatEvent.ReplyLikeButtonClick -> {
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
            getCommentsWithReply()
        }
    }

    private fun getCommentsWithReply() = viewModelScope.launch{
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
        useCases.getOwner(ownerId).collect { result ->
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
        useCases.getOwner(ownerId).collect { result ->
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
            content = _state.value.inputText,
        )
        useCases.createComment(newComment).collect { result ->
            when(result){
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        inputText = "",
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

    private fun createReply() = viewModelScope.launch {
        val commentId = _state.value.comments[_state.value.selectedCommentIdx!!].id
        val newReply = Reply(
            ownerId = _state.value.currentUser?.account?.id,
            commentId = commentId,
            content = _state.value.inputText,
        )
        useCases.createReply(newReply).collect { result ->
            when(result){
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        selectedCommentIdx = null,
                        inputText = "",
                        isLoading = false
                    )
                    _eventFlow.emit(UiEvent.ShowToast("답글을 작성하였습니다"))
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
                    _eventFlow.emit(UiEvent.ShowToast("답글을 작성하지 못했습니다 (${result.message})"))
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

                    val likeTags = _state.value.commentLikeTags.toMutableList()
                    likeTags.add(result.data!!)

                    _state.value = _state.value.copy(
                        commentLikeTags = likeTags,
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

                    val likeTags = _state.value.replyLikeTags.toMutableList()
                    likeTags.add(result.data!!)

                    _state.value = _state.value.copy(
                        replyLikeTags = likeTags,
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

                    val tags = _state.value.commentLikeTags.toMutableList()
                    tags.removeIf{it.id == tagId}

                    _state.value = _state.value.copy(
                        commentLikeTags = tags,
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

                    val tags = _state.value.replyLikeTags.toMutableList()
                    tags.removeIf{it.id == tagId}

                    _state.value = _state.value.copy(
                        replyLikeTags = tags,
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