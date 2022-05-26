package com.ich.reciptopia.presentation.main.analyze_ingredient.components

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex

@Composable
fun CapturedImageItem(
    modifier: Modifier = Modifier,
    image: Bitmap,
    contentDescription: String?,
    onDeleteClick: () -> Unit
){
    Box(
        modifier = modifier
    ){
        IconButton(
            modifier = Modifier
                .size(20.dp)
                .align(Alignment.TopEnd)
                .zIndex(1f)
                .offset(x = 8.dp, y = (-4).dp),
            onClick = onDeleteClick
        ) {
            Icon(
                modifier = Modifier
                    .clip(RoundedCornerShape(100))
                    .background(Color.Black),
                imageVector = Icons.Default.Close,
                contentDescription = "Delete Image Icon",
                tint = Color.White
            )
        }

        Image(
            modifier = Modifier.size(240.dp),
            bitmap = image.asImageBitmap(),
            contentScale = ContentScale.Crop,
            contentDescription = contentDescription
        )
    }
}