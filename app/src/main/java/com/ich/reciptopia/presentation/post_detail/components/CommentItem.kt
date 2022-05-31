package com.ich.reciptopia.presentation.post_detail.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ich.reciptopia.R
import com.ich.reciptopia.domain.model.Comment

@Composable
fun CommentItem(
    modifier: Modifier = Modifier,
    comment: Comment,
    onCommentClick: () -> Unit,
    onCommentLikeClick: () -> Unit
){
    Row(
        modifier = modifier
            .clickable { onCommentClick() }
    ) {
        IconButton(
            onClick = {}
        ) {
            Icon(
                modifier = Modifier
                    .size(50.dp)
                    .padding(8.dp),
                imageVector = Icons.Filled.AccountCircle,
                contentDescription = "",
                tint = colorResource(id = R.color.main_color)
            )
        }

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .padding(end = 4.dp),
                    text = comment.owner?.nickname ?: "(알 수 없음)",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Text(
                    modifier = Modifier.weight(1f),
                    text = "2022/03/29",
                    fontSize = 14.sp
                )

                TextButton(
                    onClick = onCommentLikeClick
                ) {
                    Icon(
                        modifier = Modifier.size(12.dp),
                        imageVector = Icons.Default.ThumbUp,
                        contentDescription = "",
                        tint = if(comment.like) colorResource(id = R.color.main_color) else Color.Gray
                    )

                    Text(
                        text = " 123",
                        color = Color.Gray
                    )
                }
            }

            Text(
                text = comment.content ?: ""
            )
        }
    }
}