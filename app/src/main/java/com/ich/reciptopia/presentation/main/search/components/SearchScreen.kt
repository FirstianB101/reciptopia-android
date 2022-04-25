package com.ich.reciptopia.presentation.main.search.components

import android.widget.Toast
import androidx.compose.foundation.layout.*
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
import com.ich.reciptopia.domain.model.SearchHistory
import com.ich.reciptopia.presentation.main.components.MainScreenUI
import com.ich.reciptopia.presentation.main.search.SearchScreenEvent
import com.ich.reciptopia.presentation.main.search.SearchState
import com.ich.reciptopia.presentation.main.search.SearchViewModel
import com.ich.reciptopia.presentation.main.search.util.ChipState
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

    LaunchedEffect(Unit){
        viewModel.eventFlow.collectLatest { event ->
            when(event){
                is SearchViewModel.UiEvent.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
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
                            onClick = { tabIndex = index },
                            text = { Text(text = title, maxLines = 1, softWrap = false) },
                        )
                    }
                }
                when (tabIndex) {
                    0 -> {
                        state.value.searchHistories.forEachIndexed { index, history ->
                            SearchHistoryListItem(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(24.dp),
                                items = history.ingredients,
                                onItemClicked = {
                                    navController.navigate(MainScreenUI.BoardListScreen.route) {
                                        popUpTo(MainScreenUI.BoardListScreen.route) {
                                            inclusive = true
                                        }
                                        launchSingleTop = true
                                    }
                                },
                                onDeleteItem = {
                                    viewModel.onEvent(SearchScreenEvent.DeleteSearchHistory(history))
                                }
                            )
                            Divider()
                        }
                    }
                    1 -> {
                        for (i in 0..4) {
                            FavoriteListItem(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(24.dp)
                            )
                            Divider()
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
                    SearchScreenEvent.AddSearchHistory(
                        SearchHistory(ingredients = chipStates.map { s -> s.toChipInfo() })
                    )
                )
                navController.navigate(MainScreenUI.BoardListScreen.route)
                onChipReset()
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