package com.ich.reciptopia.common.components

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun TopText(
    modifier: Modifier = Modifier,
    text: String
){
    Text(
        modifier = modifier,
        text = text,
        color = Color.Black,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp
    )
}