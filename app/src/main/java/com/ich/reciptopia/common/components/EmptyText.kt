package com.ich.reciptopia.common.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun EmptyText(
    modifier: Modifier = Modifier,
    text: String
){
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ){
        Text(
            modifier = modifier,
            text = text,
            fontSize = 19.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )
    }
}