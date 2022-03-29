package com.ich.reciptopia.presentation.board.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.outlined.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ich.reciptopia.R
import com.ich.reciptopia.presentation.main.search.components.CustomTextField

@Composable
fun BoardCommentBottomSheet(
    modifier: Modifier = Modifier
){
    var commentText by remember{ mutableStateOf("") }
    val interactionSource = remember { MutableInteractionSource() }

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
        
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ){
            items(4){
                CommentItem(
                    modifier = Modifier.fillMaxWidth()
                )
            }
            item{
                CommentItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 40.dp)
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0x55EEEEEE))
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CustomTextField(
                value = commentText,
                onValueChange = {commentText = it},
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
                interactionSource = interactionSource
            )

            IconButton(
                onClick = {  },
                modifier = Modifier
                    .size(40.dp)
                    .padding(2.dp)
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