package com.ich.reciptopia.common.components

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.ich.reciptopia.R

@Composable
fun ProfileImageIfExistOrAccountIcon(
    image: Bitmap?,
){
    if(image != null){
        Image(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape),
            bitmap = image.asImageBitmap(),
            contentDescription = "Profile Image",
            contentScale = ContentScale.Crop
        )
    }else{
        Icon(
            modifier = Modifier.size(36.dp),
            imageVector = Icons.Filled.AccountCircle,
            contentDescription = "Account Icon",
            tint = colorResource(id = R.color.main_color)
        )
    }
}