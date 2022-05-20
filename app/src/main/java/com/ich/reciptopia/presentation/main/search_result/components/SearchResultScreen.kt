package com.ich.reciptopia.presentation.main.search_result.components

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ich.reciptopia.presentation.community.components.PostPreviewItem
import com.ich.reciptopia.presentation.community.components.startPostActivity
import com.ich.reciptopia.presentation.main.search.SearchScreenEvent
import com.ich.reciptopia.presentation.main.search.SearchViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SearchResultScreen(
    viewModel: SearchViewModel = hiltViewModel()
){
    val context = LocalContext.current
    val state = viewModel.state.collectAsState()

    LaunchedEffect(Unit){
        viewModel.eventFlow.collectLatest { event ->
            when(event){
                is SearchViewModel.UiEvent.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ){
        itemsIndexed(state.value.posts){ idx, post ->
            Divider()
            PostPreviewItem(
                modifier = Modifier.fillMaxWidth(),
                post = post,
                owner = post.owner,
                starFilled = post.isFavorite,
                onStarClick = {
                    viewModel.onEvent(SearchScreenEvent.FavoriteButtonClicked(post))
                },
                onPostClick = {
                    startPostActivity(context, post.id!!)
                },
                onLikeClick = {
                    viewModel.onEvent(SearchScreenEvent.LikeButtonClicked(post))
                }
            )
        }
    }

    if(state.value.posts.isEmpty()){
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = "검색 결과가 없습니다",
                fontSize = 19.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}