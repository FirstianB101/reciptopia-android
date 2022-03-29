package com.ich.reciptopia.presentation.board.components

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ich.reciptopia.R

@Composable
fun CommentItem(
    modifier: Modifier = Modifier
){
    Row(
        modifier = modifier
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
                    modifier = Modifier.padding(end = 4.dp),
                    text = "nickname",
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
                    onClick = {  }
                ) {
                    Icon(
                        modifier = Modifier.size(12.dp),
                        imageVector = Icons.Default.ThumbUp,
                        contentDescription = "",
                        tint = Color.Gray
                    )

                    Text(
                        text = " 123",
                        color = Color.Gray
                    )
                }

                TextButton(
                    onClick = {}
                ) {
                    Icon(
                        modifier = Modifier.size(12.dp),
                        imageVector = Icons.Default.Chat,
                        contentDescription = "",
                        tint = Color.Gray
                    )

                    Text(
                        text = " 123",
                        color = Color.Gray
                    )
                }
            }

            Text(
                text = "comment comment comment comment comment comment comment comment comment comment "
            )
        }
    }
}