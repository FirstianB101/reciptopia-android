package com.ich.reciptopia.presentation.post_detail.components

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Chat
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.ich.reciptopia.R
import com.ich.reciptopia.presentation.post_detail.PostDetailEvent
import com.ich.reciptopia.presentation.post_detail.PostDetailViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun PostDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: PostDetailViewModel = hiltViewModel(),
    onCommentClicked: () -> Unit
){
    val state = viewModel.state.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit){
        viewModel.eventFlow.collectLatest { event ->
            when(event){
                is PostDetailViewModel.UiEvent.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 8.dp, start = 16.dp),
                text = state.value.curPost?.title ?: "",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            IconButton(
                modifier = Modifier.padding(top = 8.dp, end = 8.dp),
                onClick = {
                    viewModel.onEvent(PostDetailEvent.ClickFavorite)
                }
            ) {
                Icon(
                    modifier = Modifier.size(32.dp),
                    imageVector = if(state.value.curPost?.isFavorite == true)
                        Icons.Filled.Star else Icons.Outlined.StarBorder,
                    contentDescription = "Star Icon",
                    tint = if(state.value.curPost?.isFavorite == true)
                        Color.Yellow else Color.Gray
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            TextButton(
                modifier = Modifier.padding(8.dp),
                onClick = {}
            ) {
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = "",
                    tint = colorResource(id = R.color.main_color)
                )

                Spacer(modifier = Modifier.width(4.dp))

                Text(
                    text = state.value.curPost?.owner?.nickname ?: "",
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Text(
                modifier = Modifier.padding(end = 16.dp),
                text = "조회수 ${state.value.curPost?.views ?: 0}",
                color = Color.Gray
            )
        }

        Divider()

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ){
            state.value.curPost?.pictureUrls?.let {
                itemsIndexed(it){ idx, url ->
                    Image(
                        modifier = Modifier
                            .size(200.dp)
                            .padding(8.dp),
                        painter = rememberImagePainter(url),
                        contentScale = ContentScale.Crop,
                        contentDescription = ""
                    )
                }
            }
        }

        Text(
            modifier = Modifier
                .padding(12.dp),
            text = "메인 재료",
            fontSize = 19.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        state.value.mainIngredients.forEach {
            Text(
                modifier = Modifier.offset(x = 24.dp),
                text = "${it.name} (${it.detail}) "
            )
        }
        
        Text(
            modifier = Modifier
                .padding(12.dp),
            text = "서브 재료",
            fontSize = 19.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        state.value.subIngredients.forEach {
            Text(
                modifier = Modifier.offset(x = 24.dp),
                text = "${it.name} (${it.detail}) "
            )
        }

        Spacer(modifier = Modifier.height(12.dp))
        
        Text(
            modifier = Modifier.padding(12.dp),
            text = stringResource(id = R.string.how_to_make),
            fontSize = 19.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Column(
            modifier = Modifier.weight(1f)
        ) {
            state.value.curPostSteps.forEachIndexed { idx, step ->
                Text(
                    modifier = Modifier.offset(x = 24.dp),
                    text = "${idx + 1}. ${step.description}"
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(
                onClick = {
                    viewModel.onEvent(PostDetailEvent.ClickLike)
                }
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = Icons.Default.ThumbUp,
                    contentDescription = "",
                    tint = if(state.value.curPost?.like == true) colorResource(id = R.color.main_color)
                            else Color.Gray
                )
                
                Text(
                    text = " ${state.value.curPost?.likeCount}",
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.width(8.dp))
            
            TextButton(
                onClick = onCommentClicked
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = Icons.Default.Chat,
                    contentDescription = "",
                    tint = Color.Gray
                )

                Text(
                    text = " ${state.value.curPost?.commentCount}",
                    color = Color.Gray
                )
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        if(state.value.isLoading){
            CircularProgressIndicator()
        }
    }
}