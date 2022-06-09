package com.ich.reciptopia.presentation.post_detail.components

import android.app.Activity
import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.runtime.*
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
import com.ich.reciptopia.common.components.TwoButtonDialog
import com.ich.reciptopia.presentation.community.components.create_post.ReadOnlyStepItem
import com.ich.reciptopia.presentation.main.search.util.ChipState
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

    var deleteDialogState by remember { mutableStateOf(false) }
    var editDialogState by remember { mutableStateOf(false) }
            
    LaunchedEffect(Unit){
        viewModel.eventFlow.collectLatest { event ->
            when(event){
                is PostDetailViewModel.UiEvent.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
                is PostDetailViewModel.UiEvent.GoToPostList -> {
                    (context as Activity).finish()
                }
            }
        }
    }

    Column(modifier = modifier){
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

            if(state.value.isOwner) {
                Box(
                    modifier = Modifier.size(32.dp)
                ) {
                    IconButton(
                        modifier = Modifier.padding(top = 8.dp, end = 8.dp),
                        onClick = {
                            viewModel.onEvent(PostDetailEvent.ShowSettingMenu(true))
                        }
                    ) {
                        Icon(
                            modifier = Modifier.size(32.dp),
                            imageVector = Icons.Outlined.Settings,
                            contentDescription = "Setting Icon",
                            tint = Color.Gray
                        )
                    }

                    DropdownMenu(
                        expanded = state.value.showSettingMenu,
                        onDismissRequest = { viewModel.onEvent(PostDetailEvent.ShowSettingMenu(false)) },
                    ) {
                        DropdownMenuItem(
                            onClick = {
                                editDialogState = true
                                viewModel.onEvent(PostDetailEvent.ShowSettingMenu(false))
                            }
                        ) {
                            Text(
                                text = stringResource(id = R.string.edit_post)
                            )
                        }
                        DropdownMenuItem(
                            onClick = {
                                deleteDialogState = true
                                viewModel.onEvent(PostDetailEvent.ShowSettingMenu(false))
                            }
                        ) {
                            Text(
                                text = stringResource(id = R.string.delete_post),
                                color = Color.Red
                            )
                        }
                    }
                }
            }

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

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            item {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    state.value.curPost?.pictureUrls?.let {
                        itemsIndexed(it) { idx, url ->
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
            }

            item{
                Text(
                    modifier = Modifier.padding(12.dp),
                    text = "메인 재료",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Divider()
            }

            items(state.value.mainIngredients){
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                ){
                    Text(
                        modifier = Modifier.offset(x = 24.dp),
                        text = "${it.name}",
                        color = Color.Black
                    )
                    
                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        modifier = Modifier.offset(x = (-24).dp),
                        text = "${it.detail}",
                        color = Color.Black
                    )
                }
                Divider()
            }

            item{
                Text(
                    modifier = Modifier
                        .padding(top = 16.dp, start = 12.dp, end = 12.dp, bottom = 12.dp),
                    text = "부가 재료",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Divider()
            }

            items(state.value.subIngredients){
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                ){
                    Text(
                        modifier = Modifier.offset(x = 24.dp),
                        text = "${it.name}",
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        modifier = Modifier.offset(x = (-24).dp),
                        text = "${it.detail}",
                        color = Color.Black
                    )
                }
                Divider()
            }

            item{
                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    modifier = Modifier.padding(12.dp),
                    text = stringResource(id = R.string.recipe_steps),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Divider()
            }

            itemsIndexed(state.value.curPostSteps){ idx, step ->
                ReadOnlyStepItem(
                    modifier = Modifier.fillMaxWidth(),
                    index = idx + 1,
                    step = step,
                    backgroundColor = Color.White,
                    contentColor = Color.LightGray
                )
            }

            item{
                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    modifier = Modifier.padding(12.dp),
                    text = "레시피 정보",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Text(
                    modifier = Modifier.padding(12.dp),
                    text = state.value.curPost?.content ?: "",
                    color = Color.Black
                )
            }
        }

        Divider()

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
                    modifier = Modifier
                        .size(24.dp)
                        .padding(end = 4.dp),
                    imageVector = Icons.Default.ThumbUp,
                    contentDescription = "",
                    tint = if (state.value.curPost?.like == true) colorResource(id = R.color.main_color)
                    else Color.Gray
                )

                AnimatedLikeCounter(
                    count = state.value.curPost?.likeCount ?: 0,
                    like = state.value.curPost?.like ?: false
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
    
    TwoButtonDialog(
        modifier = Modifier.padding(16.dp),
        title = stringResource(id = R.string.delete_post),
        content = stringResource(id = R.string.comment_delete_post),
        dialogState = deleteDialogState,
        cancelText = stringResource(id = R.string.cancel),
        confirmText = stringResource(id = R.string.confirm),
        onCancel = { deleteDialogState = false }
    ) {
        viewModel.onEvent(PostDetailEvent.DeletePost)
        deleteDialogState = false
    }

    if(state.value.curPost != null) {
        val initialChips = mutableListOf<ChipState>()
        state.value.mainIngredients.forEach { initialChips.add(
            ChipState(it.name ?: "", mutableStateOf(false), it.detail ?: "")
        )}
        state.value.subIngredients.forEach { initialChips.add(
            ChipState(it.name ?: "", mutableStateOf(true), it.detail ?: "")
        )}
        EditPostDialog(
            showDialog = editDialogState,
            initialPost = state.value.curPost!!,
            initialSteps = state.value.curPostSteps,
            initialChips = initialChips,
            onEdit = { post, steps, chips ->

            },
            onClose = { editDialogState = false }
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun AnimatedLikeCounter(
    count: Int,
    like: Boolean
){
    AnimatedContent(
        targetState = count,
        transitionSpec = {
            if (targetState > initialState) {
                slideInVertically { height -> height } + fadeIn() with
                        slideOutVertically { height -> -height } + fadeOut()
            } else {
                slideInVertically { height -> -height } + fadeIn() with
                        slideOutVertically { height -> height } + fadeOut()
            }.using(
                SizeTransform(clip = false)
            )
        }
    ) { targetCount ->
        Text(
            text = "$targetCount",
            color = if(like) colorResource(id = R.color.main_color) else Color.Gray
        )
    }
}