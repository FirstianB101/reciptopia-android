package com.ich.reciptopia.presentation.notification.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ich.reciptopia.R

@Composable
fun NotificationScreen(){
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        val notifications = listOf(
            listOf("nickname 님이 Board1를 좋아합니다", "", "YYYY년 MM월 dd일 HH:mm"),
            listOf("nickname 님이 댓글을 남겼어요", "댓글댓글댓글댓글댓글댓글댓글댓글댓글", "YYYY년 MM월 dd일 HH:mm"),
            listOf("nickname 님이 Board1를 좋아합니다", "", "YYYY년 MM월 dd일 HH:mm"),
            listOf("nickname 님이 댓글을 남겼어요", "댓글댓글댓글댓글댓글댓글댓글댓글댓글", "YYYY년 MM월 dd일 HH:mm"),
            listOf("nickname 님이 Board1를 좋아합니다", "", "YYYY년 MM월 dd일 HH:mm"),
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            text = stringResource(id = R.string.notification),
            color = Color.Black,
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Divider()

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ){
            items(notifications.size){ i ->
                NotificationItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    title = notifications[i][0],
                    content = notifications[i][1],
                    timestamp = notifications[i][2],
                    readBefore = i == 2 || i == 3
                ) {

                }

                Divider()
            }
        }
    }
}