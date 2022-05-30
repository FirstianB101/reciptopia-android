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
import androidx.compose.material.icons.filled.SubdirectoryArrowRight
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
import com.ich.reciptopia.domain.model.Reply

@Composable
fun ReplyItem(
    modifier: Modifier = Modifier,
    reply: Reply,
    onReplyClick: () -> Unit,
    onReplyLikeClick: () -> Unit
){
    Row(
        modifier = modifier
            .clickable { onReplyClick() }
            .padding(start = 40.dp)
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
                        .weight(1f)
                        .padding(end = 4.dp),
                    text = reply.owner?.nickname ?: "(알 수 없음)",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                TextButton(
                    onClick = onReplyLikeClick
                ) {
                    Icon(
                        modifier = Modifier.size(12.dp),
                        imageVector = Icons.Default.ThumbUp,
                        contentDescription = "",
                        tint = if(reply.like) colorResource(id = R.color.main_color) else Color.Gray
                    )

                    Text(
                        text = " 123",
                        color = Color.Gray
                    )
                }
            }

            Text(
                text = reply.content ?: ""
            )
        }
    }
}