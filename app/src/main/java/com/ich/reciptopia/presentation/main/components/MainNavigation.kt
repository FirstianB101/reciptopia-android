package com.ich.reciptopia.presentation.main.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.ich.reciptopia.presentation.board.components.BoardListScreen
import com.ich.reciptopia.presentation.main.analyze_ingredient.components.AnalyzeIngredientScreen
import com.ich.reciptopia.presentation.main.search.SearchScreenEvent
import com.ich.reciptopia.presentation.main.search.SearchViewModel
import com.ich.reciptopia.presentation.main.search.components.SearchScreen
import com.ich.reciptopia.presentation.main.search.util.ChipState

@Composable
fun MainNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: SearchViewModel = hiltViewModel(),
    loginButtonClicked: () -> Unit,
    notificationButtonClicked: () -> Unit
){
    val state = viewModel.state.collectAsState()

    val chipStates = remember { mutableStateListOf<ChipState>() }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val searchSource = remember { MutableInteractionSource() }

    LaunchedEffect(currentRoute) {
        when (currentRoute) {
            MainScreenUI.CameraScreen.route ->
                viewModel.onEvent(SearchScreenEvent.SearchModeChanged(false))
        }
    }

    if (searchSource.collectIsPressedAsState().value) {
        if (currentRoute != MainScreenUI.SearchScreen.route)
            navController.navigate(MainScreenUI.SearchScreen.route)
    }

    Scaffold(
        topBar = {
            SearchableTopBar(
                modifier = Modifier.fillMaxWidth(),
                searchMode = state.value.searchMode,
                searchText = state.value.searchQuery,
                searchSource = searchSource,
                onLoginButtonClicked = loginButtonClicked,
                onNotificationButtonClicked = notificationButtonClicked,
                onAddChip = {
                    chipStates.add(ChipState(state.value.searchQuery, mutableStateOf(true)))
                },
                onSearchTextChanged = { viewModel.onEvent(SearchScreenEvent.SearchQueryChanged(it)) },
                onSearchTextReset = { viewModel.onEvent(SearchScreenEvent.SearchQueryChanged("")) },
                onSearchButtonClicked = {
                    viewModel.onEvent(SearchScreenEvent.SearchModeChanged(true))
                    navController.navigate(MainScreenUI.SearchScreen.route)
                }
            )
        }
    ) {
        NavHost(
            modifier = modifier,
            navController = navController,
            startDestination = MainScreenUI.CameraScreen.route
        ) {
            composable(route = MainScreenUI.CameraScreen.route) {
                AnalyzeIngredientScreen()
            }
            composable(route = MainScreenUI.SearchScreen.route) {
                SearchScreen(
                    chipStates = chipStates,
                    navController = navController,
                    onChipClicked = { content, isMain, idx ->
                        chipStates[idx].isSubIngredient.value = !chipStates[idx].isSubIngredient.value
                    },
                    onDeleteClicked = { content, isMain, idx ->
                        chipStates.removeAt(idx)
                    },
                    onChipReset = {
                        chipStates.clear()
                    }
                )
            }
            composable(route = MainScreenUI.BoardListScreen.route) {
                BoardListScreen()
            }
        }
    }
}