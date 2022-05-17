package com.ich.reciptopia.presentation.community.components

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ich.reciptopia.R
import com.ich.reciptopia.presentation.post_detail.PostActivity
import com.ich.reciptopia.presentation.community.CommunityScreenEvent
import com.ich.reciptopia.presentation.community.CommunityViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun CommunityScreen(
    modifier: Modifier = Modifier,
    viewModel: CommunityViewModel = hiltViewModel(),
    onLoginButtonClicked: () -> Unit
){
    val state = viewModel.state.collectAsState()
    val options = listOf("최신순", "조회순")
    var expanded by remember { mutableStateOf(false) }
    val context = LocalContext.current

    BackHandler(state.value.searchMode) {
        viewModel.onEvent(CommunityScreenEvent.SearchModeOff)
    }

    LaunchedEffect(Unit){
        viewModel.eventFlow.collectLatest { event ->
            when(event){
                is CommunityViewModel.UiEvent.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
                is CommunityViewModel.UiEvent.SuccessCreatePost -> {
                    viewModel.onEvent(CommunityScreenEvent.CreatePostStateChanged(false))
                }
            }
        }
    }
    
    Scaffold(
        modifier = modifier,
        topBar = {
            CommunityTopBar(
                modifier = Modifier.fillMaxWidth(),
                searchMode = state.value.searchMode,
                searchText = state.value.searchQuery,
                onLoginButtonClicked = onLoginButtonClicked,
                onSearchTextChanged = { viewModel.onEvent(CommunityScreenEvent.SearchQueryChanged(it)) },
                onSearchButtonClicked = { viewModel.onEvent(CommunityScreenEvent.SearchButtonClicked) }
            )
        }
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ){
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Box(
                    modifier = Modifier.align(Alignment.End)
                ) {
                    TextButton(
                        modifier = Modifier.padding(top = 8.dp, end = 8.dp),
                        onClick = { expanded = !expanded }
                    ) {
                        Icon(
                            imageVector = Icons.Default.FilterList,
                            contentDescription = null,
                            tint = Color.Black
                        )
                        Text(
                            text = state.value.sortOption,
                            color = Color.Black
                        )
                    }
                    DropdownMenu(
                        modifier = Modifier.width(85.dp),
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                    ) {
                        options.forEach { label ->
                            DropdownMenuItem(onClick = {
                                expanded = false
                                viewModel.onEvent(CommunityScreenEvent.SortOptionChanged(label))
                            }) {
                                Text(text = label)
                            }
                        }
                    }
                }

                LazyColumn(
                    modifier = Modifier.fillMaxWidth()
                ){
                    itemsIndexed(state.value.posts){ idx, post ->
                        Divider()
                        PostPreviewItem(
                            modifier = Modifier.fillMaxWidth(),
                            post = post,
                            owner = post.owner!!,
                            starFilled = post.favoriteNotLogin,
                            onStarClick = {
                                viewModel.onEvent(CommunityScreenEvent.FavoriteButtonClicked(post))
                            },
                            onPostClick = {
                                startPostActivity(context, post.id!!)
                            }
                        )
                    }
                }
            }

            FloatingActionButton(
                modifier = Modifier
                    .size(48.dp)
                    .align(Alignment.BottomEnd)
                    .offset(x = (-16).dp, y = (-16).dp),
                onClick = { viewModel.onEvent(CommunityScreenEvent.CreatePostStateChanged(true)) },
                backgroundColor = colorResource(id = R.color.main_color),
                contentColor = Color.White
            ) {
                Icon(
                    imageVector = Icons.Filled.Create,
                    contentDescription = "Community Write FAB"
                )
            }
        }
    }

    CreatePostDialog(
        showDialog = state.value.showCreatePostDialog,
    ) {
        viewModel.onEvent(CommunityScreenEvent.CreatePostStateChanged(false))
    }
}

private fun startPostActivity(context: Context, postId: Long){
    val intent = PostActivity.getPostIntent(context).apply {
        putExtra("selectedPostId",postId)
    }
    context.startActivity(intent)
}