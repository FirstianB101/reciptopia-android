package com.ich.reciptopia.presentation.post_detail.chat.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ich.reciptopia.R
import com.ich.reciptopia.common.components.EmptyText
import com.ich.reciptopia.presentation.main.search.components.CustomTextField
import com.ich.reciptopia.presentation.post_detail.chat.PostDetailChatEvent
import com.ich.reciptopia.presentation.post_detail.chat.PostDetailChatViewModel
import com.ich.reciptopia.presentation.post_detail.components.CommentItem
import com.ich.reciptopia.presentation.post_detail.components.ReplyItem
import kotlinx.coroutines.flow.collectLatest

@Composable
fun PostCommentBottomSheet(
    modifier: Modifier = Modifier,
    postId: Long,
    viewModel: PostDetailChatViewModel = hiltViewModel()
){
    val state = viewModel.state.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit){
        viewModel.initialize(postId)
        viewModel.eventFlow.collectLatest { event ->
            when(event){
                is PostDetailChatViewModel.UiEvent.ShowToast ->{
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = stringResource(id = R.string.comment),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        if(state.value.comments.isEmpty()){
            EmptyText(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                text = stringResource(id = R.string.comment_no_comment)
            )
        }
        
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ){
            itemsIndexed(state.value.comments){ cIdx, comment ->
                val commentBackground =
                    if(cIdx == state.value.selectedCommentIdx) colorResource(R.color.selected_comment_bg)
                    else Color.White
                CommentItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(commentBackground)
                        .padding(8.dp)
                        .offset(y = (-4).dp),
                    comment = comment,
                    onCommentClick = {
                        viewModel.onEvent(PostDetailChatEvent.SelectComment(cIdx))
                    },
                    onCommentLikeClick = {
                        viewModel.onEvent(PostDetailChatEvent.CommentLikeButtonClick(comment, cIdx))
                    }
                )
                comment.replies?.forEachIndexed { rIdx, reply ->
                    ReplyItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                            .offset(y = (-4).dp),
                        reply = reply,
                        onReplyClick = {},
                        onReplyLikeClick = {
                            viewModel.onEvent(PostDetailChatEvent.ReplyLikeButtonClick(reply, cIdx, rIdx))
                        }
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0x55EEEEEE))
                .padding(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val selectedCommentExist = state.value.selectedCommentIdx != null
            CustomTextField(
                value = state.value.inputText,
                onValueChange = { viewModel.onEvent(PostDetailChatEvent.CommentTextChanged(it)) },
                modifier = Modifier
                    .background(
                        Color(0xDDDDDDDD),
                        RoundedCornerShape(10.dp)
                    )
                    .padding(4.dp)
                    .height(36.dp)
                    .weight(1f),
                fontSize = 16.sp,
                placeholderText = if(selectedCommentExist) stringResource(R.string.comment_input_reply)
                                  else stringResource(id = R.string.comment_input_comment),
                interactionSource =  MutableInteractionSource()
            )
            
            Spacer(modifier = Modifier.width(6.dp))

            IconButton(
                onClick = {
                    viewModel.onEvent(PostDetailChatEvent.CreateCommentOrReply)
                },
                modifier = Modifier
                    .size(40.dp)
                    .padding(4.dp)
                    .clip(CircleShape)
                    .background(colorResource(id = R.color.main_color))
            ) {
                Icon(
                    imageVector = Icons.Filled.Create,
                    contentDescription = "",
                    tint = Color.White
                )
            }
        }
    }
}