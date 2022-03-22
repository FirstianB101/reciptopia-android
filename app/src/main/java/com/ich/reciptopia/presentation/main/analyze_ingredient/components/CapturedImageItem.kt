package com.ich.reciptopia.presentation.main.analyze_ingredient.components

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.ich.reciptopia.R

@Composable
fun CapturedImageItem(
    modifier: Modifier = Modifier,
    image: Bitmap,
    contentDescription: String?,
    showCheckIcon: Boolean,
    isChecked: Boolean,
    onCheckedChanged: (Boolean) -> Unit
){
    Box(
        modifier = modifier
    ){
        if(showCheckIcon){
            Checkbox(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .zIndex(1f)
                    .padding(end = 8.dp, top = 8.dp),
                checked = isChecked,
                onCheckedChange = onCheckedChanged,
                colors = CheckboxDefaults.colors(
                    uncheckedColor = colorResource(id = R.color.main_ingredient),
                    checkedColor = colorResource(id = R.color.main_ingredient),
                    checkmarkColor = Color.White
                )
            )
        }

        Image(
            bitmap = image.asImageBitmap(),
            contentDescription = contentDescription
        )
    }
}