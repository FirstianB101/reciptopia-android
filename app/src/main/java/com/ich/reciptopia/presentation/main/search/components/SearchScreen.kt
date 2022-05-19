package com.ich.reciptopia.presentation.main.search.components

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ich.reciptopia.R
import com.ich.reciptopia.common.util.TestTags
import com.ich.reciptopia.presentation.community.components.PostPreviewItem
import com.ich.reciptopia.presentation.main.components.MainScreenUI
import com.ich.reciptopia.presentation.main.search.SearchScreenEvent
import com.ich.reciptopia.presentation.main.search.SearchViewModel
import com.ich.reciptopia.presentation.main.search.util.ChipState
import com.ich.reciptopia.presentation.post_detail.PostActivity
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    chipStates: List<ChipState>,
    navController: NavController,
    viewModel: SearchViewModel = hiltViewModel(),
    onChipClicked: (String, Boolean, Int) -> Unit,
    onDeleteClicked: (String, Boolean, Int) -> Unit,
    onChipReset: () -> Unit
) {
    val state = viewModel.state.collectAsState()
    val context = LocalContext.current

    var tabIndex by remember { mutableStateOf(0) }
    val tabTitles = listOf(
        stringResource(id = R.string.search_history),
        stringResource(id = R.string.favorite)
    )

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is SearchViewModel.UiEvent.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
                is SearchViewModel.UiEvent.NavigateToSearchResultScreen -> {
                    navController.navigate(MainScreenUI.PostListScreen.route) {
                        popUpTo(MainScreenUI.PostListScreen.route) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                    onChipReset()
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .testTag(TestTags.SEARCH_SCREEN)
    ) {
        Column(
            modifier = modifier
        ) {
            Chips(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                elements = chipStates.map { s -> s.text },
                selectStates = chipStates.map { s -> s.isSubIngredient.value },
                onChipClicked = onChipClicked,
                onImageClicked = onDeleteClicked
            )

            Column {
                TabRow(
                    selectedTabIndex = tabIndex,
                    modifier = Modifier.fillMaxWidth(),
                    backgroundColor = Color.White
                ) {
                    tabTitles.forEachIndexed { index, title ->
                        Tab(
                            selected = tabIndex == index,
                            onClick = {
                                tabIndex = index
                                if(tabIndex == 0) viewModel.onEvent(SearchScreenEvent.GetSearchHistories)
                                else viewModel.onEvent(SearchScreenEvent.GetFavoritePosts)
                            },
                            text = { Text(text = title, maxLines = 1, softWrap = false) },
                        )
                    }
                }


                when (tabIndex) {
                    0 -> {
                        LazyColumn(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            itemsIndexed(state.value.searchHistories) { index, history ->
                                SearchHistoryListItem(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(24.dp),
                                    items = history.ingredientNames,
                                    onItemClicked = {
                                        viewModel.onEvent(SearchScreenEvent.ClickHistory(history))
                                    },
                                    onDeleteItem = {
                                        viewModel.onEvent(SearchScreenEvent.DeleteSearchHistory(history))
                                    }
                                )
                                Divider()
                            }
                        }
                    }
                    1 -> {
                        LazyColumn(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            itemsIndexed(state.value.favorites) { index, favorite ->
                                PostPreviewItem(
                                    modifier = Modifier.fillMaxWidth(),
                                    post = favorite.post,
                                    owner = favorite.post?.owner,
                                    starFilled = true,
                                    onStarClick = {
                                        viewModel.onEvent(SearchScreenEvent.DeleteFavorite(favorite))
                                    },
                                    onPostClick = {
                                        startPostActivity(context, favorite.post?.id!!)
                                    },
                                    onLikeClick = {

                                    }
                                )
                                Divider()
                            }
                        }
                    }
                }
            }
        }

        FloatingActionButton(
            modifier = Modifier
                .size(48.dp)
                .align(Alignment.BottomEnd)
                .offset(x = (-16).dp, y = (-16).dp)
                .testTag(TestTags.SEARCH_SCREEN_SEARCH_BUTTON),
            onClick = {
                viewModel.onEvent(
                    SearchScreenEvent.DoSearch(
                        //SearchHistory(ingredients = chipStates.map { s -> s.toChipInfo() })
                        ingredientNames = chipStates.map {s -> s.text}
                    )
                )
            },
            backgroundColor = colorResource(id = R.color.main_color),
            contentColor = Color.White
        ) {
            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = "Search FAB"
            )
        }
    }
}

private fun startPostActivity(context: Context, postId: Long?) {
    val intent = PostActivity.getPostIntent(context).apply {
        putExtra("selectedPostId", postId)
    }
    context.startActivity(intent)
}