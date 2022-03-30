package com.ich.reciptopia.presentation.main.community.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex

@Composable
fun CreateBoardImageItem(
    modifier: Modifier = Modifier,
    bitmap: ImageBitmap,
    imageSize: Dp,
    onDeleteButtonClicked: () -> Unit
){
    Box(
        modifier = modifier
    ){
        Image(
            modifier = Modifier.size(imageSize),
            bitmap = bitmap,
            contentScale = ContentScale.Crop,
            contentDescription = "Create Board Image Item"
        )

        IconButton(
            modifier = Modifier
                .clip(CircleShape)
                .size(16.dp)
                .background(Color.Black)
                .align(Alignment.TopEnd)
                .zIndex(1f),
            onClick = onDeleteButtonClicked
        ) {
            Icon(
                imageVector = Icons.Default.Clear,
                contentDescription = "Create Board Image Item Delete Icon",
                tint = Color.White
            )
        }
    }
}