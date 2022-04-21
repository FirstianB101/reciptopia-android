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
import com.ich.reciptopia.presentation.main.search.SearchState
import com.ich.reciptopia.presentation.main.search.SearchViewModel
import com.ich.reciptopia.presentation.main.search.util.ChipState

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    chipStates: List<ChipState>,
    navController: NavController,
    viewModel: SearchViewModel = hiltViewModel(),
    onChipClicked: (String, Boolean, Int) -> Unit,
    onDeleteClicked: (String, Boolean, Int) -> Unit,
    onChipReset: () -> Unit
){
    val state = viewModel.state.collectAsState()
    val context = LocalContext.current

    var tabIndex by remember { mutableStateOf(0) }
    val tabTitles = listOf(
        stringResource(id = R.string.search_history),
        stringResource(id = R.string.favorite)
    )

    var histories by remember { mutableStateOf(listOf<SearchHistory>()) }

    when(state.value){
        is SearchState.AddSearchHistory -> {
            Toast.makeText(context, "검색 기록 추가", Toast.LENGTH_SHORT).show()
        }
        is SearchState.DeleteSearchHistory -> {
            Toast.makeText(context, "검색 기록 삭제", Toast.LENGTH_SHORT).show()
        }
        is SearchState.GetSearchHistory -> {
            histories = (state.value as SearchState.GetSearchHistory).histories
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .testTag(TestTags.SEARCH_SCREEN)
    ){
        Column(
            modifier = modifier
        ) {
            Chips(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                elements = chipStates.map{ s->s.text },
                selectStates = chipStates.map{ s->s.isSubIngredient.value },
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
                            text = { Text(text = title, maxLines = 1, softWrap = false)},
                        )
                    }
                }
                when (tabIndex) {
                    0 -> {
                        histories.forEachIndexed { index, history ->
                            SearchHistoryListItem(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(24.dp),
                                items = history.ingredients,
                                onItemClicked = {
                                    navController.navigate(MainScreenUI.BoardListScreen.route){
                                        popUpTo(MainScreenUI.BoardListScreen.route){ inclusive = true }
                                        launchSingleTop = true
                                    }
                                },
                                onDeleteItem = {
                                    viewModel.deleteSearchHistory(history)
                                }
                            )
                            Divider()
                        }
                    }
                    1 -> {
                        for(i in 0..4) {
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
                .offset(x = (-16).dp, y = (-16).dp),
            onClick = {
                val newHistory = SearchHistory(ingredients = chipStates.map{s->s.toChipInfo()})
                viewModel.addSearchHistory(newHistory)
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