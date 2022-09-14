package com.ich.reciptopia.presentation.post_detail.chat.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
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
import com.ich.reciptopia.common.components.ProfileImageIfExistOrAccountIcon
import com.ich.reciptopia.domain.model.Reply

@Composable
fun ReplyItem(
    modifier: Modifier = Modifier,
    reply: Reply,
    onReplyClick: () -> Unit,
    onReplyLikeClick: () -> Unit,
){
    Row(
        modifier = Modifier
            .clickable { onReplyClick() }
            .padding(start = 40.dp)
            .then(modifier),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier.padding(8.dp)
        ) {
            ProfileImageIfExistOrAccountIcon(reply.owner?.profileImage)
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
                    text = reply.owner?.nickname ?: "",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Text(
                    text = reply.createTime?.split('T')?.get(0) ?: "",
                    fontSize = 14.sp
                )
                
                Spacer(modifier = Modifier.weight(1f))

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
                        text = " 0",
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