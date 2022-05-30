package com.ich.reciptopia.presentation.post_detail.components

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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ich.reciptopia.R
import com.ich.reciptopia.presentation.main.search.components.CustomTextField
import com.ich.reciptopia.presentation.post_detail.PostDetailEvent
import com.ich.reciptopia.presentation.post_detail.PostDetailViewModel

@Composable
fun PostCommentBottomSheet(
    modifier: Modifier = Modifier,
    viewModel: PostDetailViewModel = hiltViewModel()
){
    val state = viewModel.state.collectAsState()

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
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                text = stringResource(id = R.string.comment_no_comment),
                fontSize = 19.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            )
        }
        
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ){
            itemsIndexed(state.value.comments){ cIdx, comment ->
                CommentItem(
                    modifier = Modifier.fillMaxWidth(),
                    comment = comment,
                    onCommentClick = {},
                    onCommentLikeClick = {
                        viewModel.onEvent(PostDetailEvent.CommentLikeButtonClick(comment, cIdx))
                    }
                )
                Spacer(modifier = Modifier.height(4.dp))
                comment.replies?.forEachIndexed { rIdx, reply ->
                    ReplyItem(
                        modifier = Modifier.fillMaxWidth(),
                        reply = reply,
                        onReplyClick = {},
                        onReplyLikeClick = {
                            viewModel.onEvent(PostDetailEvent.ReplyLikeButtonClick(reply, cIdx, rIdx))
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
            CustomTextField(
                value = state.value.commentText,
                onValueChange = { viewModel.onEvent(PostDetailEvent.CommentTextChanged(it)) },
                modifier = Modifier
                    .background(
                        Color(0xDDDDDDDD),
                        RoundedCornerShape(10.dp)
                    )
                    .padding(4.dp)
                    .height(36.dp)
                    .weight(1f),
                fontSize = 16.sp,
                placeholderText = stringResource(id = R.string.comment_input_comment),
                interactionSource =  MutableInteractionSource()
            )
            
            Spacer(modifier = Modifier.width(6.dp))

            IconButton(
                onClick = {
                    viewModel.onEvent(PostDetailEvent.CreateComment)
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