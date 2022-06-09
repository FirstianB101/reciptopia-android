package com.ich.reciptopia.presentation.main.components

import android.widget.Toast
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.ich.reciptopia.presentation.main.MainScreenEvent
import com.ich.reciptopia.presentation.main.MainViewModel
import com.ich.reciptopia.presentation.main.analyze_ingredient.components.AnalyzeIngredientScreen
import com.ich.reciptopia.presentation.main.search.SearchViewModel
import com.ich.reciptopia.presentation.main.search.components.SearchScreen
import com.ich.reciptopia.presentation.main.search_result.components.SearchResultScreen
import kotlinx.coroutines.flow.collectLatest

@Composable
fun MainNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: MainViewModel = hiltViewModel(),
    loginButtonClicked: () -> Unit,
    notificationButtonClicked: () -> Unit
) {
    val state = viewModel.state.collectAsState()
    val context = LocalContext.current

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val searchViewModel: SearchViewModel = hiltViewModel()

    val searchSource = remember { MutableInteractionSource() }

    LaunchedEffect(Unit){
        viewModel.eventFlow.collectLatest { event ->
            when(event){
                is MainViewModel.UiEvent.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

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
                profileImage = state.value.currentUser?.account?.profileImage,
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
                    },
                    onChipStatesChange = {
                        viewModel.onEvent(MainScreenEvent.AddChips(it))
                    },
                    viewModel = searchViewModel
                )
            }
            composable(route = MainScreenUI.PostListScreen.route) {
                SearchResultScreen(searchViewModel)
            }
        }
    }
}