package com.ich.reciptopia.presentation.main.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.ich.reciptopia.presentation.community.components.BoardListScreen
import com.ich.reciptopia.presentation.main.MainScreenEvent
import com.ich.reciptopia.presentation.main.MainViewModel
import com.ich.reciptopia.presentation.main.analyze_ingredient.components.AnalyzeIngredientScreen
import com.ich.reciptopia.presentation.main.search.components.SearchScreen

@Composable
fun MainNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: MainViewModel = hiltViewModel(),
    loginButtonClicked: () -> Unit,
    notificationButtonClicked: () -> Unit
) {
    val state = viewModel.state.collectAsState()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val searchSource = remember { MutableInteractionSource() }

    LaunchedEffect(currentRoute) {
        when (currentRoute) {
            MainScreenUI.CameraScreen.route ->
                viewModel.onEvent(MainScreenEvent.SearchModeChanged(false))
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
                    viewModel.onEvent(MainScreenEvent.AddChip)
                },
                onSearchTextChanged = { viewModel.onEvent(MainScreenEvent.SearchQueryChanged(it)) },
                onSearchTextReset = { viewModel.onEvent(MainScreenEvent.SearchQueryChanged("")) },
                onSearchButtonClicked = {
                    viewModel.onEvent(MainScreenEvent.SearchModeChanged(true))
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
                AnalyzeIngredientScreen(navController) {
                    viewModel.onEvent(MainScreenEvent.SetChipsFromAnalyze(it))
                }
            }
            composable(route = MainScreenUI.SearchScreen.route) {
                SearchScreen(
                    chipStates = state.value.chipStates,
                    navController = navController,
                    onChipClicked = { content, isMain, idx ->
                        viewModel.onEvent(MainScreenEvent.OnChipClicked(idx))
                    },
                    onDeleteClicked = { content, isMain, idx ->
                        viewModel.onEvent(MainScreenEvent.RemoveChip(state.value.chipStates[idx]))
                    },
                    onChipReset = {
                        viewModel.onEvent(MainScreenEvent.ResetChips)
                    }
                )
            }
            composable(route = MainScreenUI.BoardListScreen.route) {
                BoardListScreen()
            }
        }
    }
}