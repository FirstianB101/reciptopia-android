package com.ich.reciptopia.presentation.community.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.ich.reciptopia.R
import com.ich.reciptopia.common.components.ProfileImageIfExistOrAccountIcon
import com.ich.reciptopia.domain.model.Account
import com.ich.reciptopia.domain.model.Post
import com.ich.reciptopia.presentation.community.CommunityScreenEvent
import com.ich.reciptopia.presentation.community.CommunityViewModel

@Composable
fun PostPreviewItem(
    modifier: Modifier = Modifier,
    post: Post?,
    starFilled: Boolean,
    owner: Account?,
    onStarClick: () -> Unit,
    onPostClick: () -> Unit,
    onLikeClick: () -> Unit
){
    Column(
        modifier = modifier.clickable { onPostClick() }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 8.dp, start = 16.dp),
                text = post?.title ?: "",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            IconButton(
                modifier = Modifier.padding(top = 8.dp, end = 8.dp),
                onClick = onStarClick
            ) {
                Icon(
                    modifier = Modifier.size(32.dp),
                    imageVector = if(starFilled) Icons.Filled.Star else Icons.Outlined.StarBorder,
                    contentDescription = "Star Icon",
                    tint = if(starFilled) Color.Yellow else Color.Gray
                )
            }
        }

        Row(
            modifier = Modifier.padding(start = 16.dp, end = 8.dp, bottom = 8.dp, top = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ProfileImageIfExistOrAccountIcon(image = owner?.profileImage)
            
            Spacer(modifier = Modifier.width(8.dp))
            
            Text(
                text = owner?.nickname ?: "",
                color = Color.Black
            )
        }

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp),
            text = post?.content ?: "",
            color = Color.Gray
        )

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ){
            if(post?.pictureUrls?.size != null) {
                items(post.pictureUrls.size) { idx ->
                    Image(
                        modifier = Modifier
                            .size(200.dp)
                            .padding(8.dp),
                        contentScale = ContentScale.Crop,
                        painter = rememberImagePainter(post.pictureUrls[idx]),
                        contentDescription = ""
                    )
                }
            }
        }
        
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = "조회수 ${post?.views ?: 0}",
                color = Color.Gray
            )
            Spacer(modifier = Modifier.width(12.dp))

            IconButton(
                modifier = Modifier.size(16.dp),
                onClick = onLikeClick
            ) {
                Icon(
                    modifier = Modifier.size(16.dp),
                    imageVector = Icons.Filled.ThumbUp,
                    contentDescription = "",
                    tint = if(post?.like == true) colorResource(id = R.color.main_color) else Color.Gray
                )
            }

            Spacer(modifier = Modifier.width(4.dp))
        }
    }
}