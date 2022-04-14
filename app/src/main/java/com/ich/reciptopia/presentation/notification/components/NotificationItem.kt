package com.ich.reciptopia.presentation.notification.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ich.reciptopia.R

@Composable
fun NotificationItem(
    modifier: Modifier = Modifier,
    title: String,
    content: String,
    timestamp: String,
    readBefore: Boolean,
    onNotificationClicked: () -> Unit
){
    Column(
        modifier = Modifier
            .background(if (readBefore) Color.White else colorResource(R.color.new_notification_background))
            .clickable { onNotificationClicked() }
            .then(modifier)
    ) {
        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = content,
            fontSize = 12.sp,
            fontWeight = FontWeight.Light,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = timestamp,
            fontSize = 14.sp,
            color = Color.Black
        )
    }
}