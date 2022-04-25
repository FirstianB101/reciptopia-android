package com.ich.reciptopia.presentation.main.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.ich.reciptopia.presentation.board.components.BoardListScreen
import com.ich.reciptopia.presentation.main.analyze_ingredient.components.AnalyzeIngredientScreen
import com.ich.reciptopia.presentation.main.search.components.SearchScreen
import com.ich.reciptopia.presentation.main.search.util.ChipState

@Composable
fun MainNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    loginButtonClicked: () -> Unit,
    notificationButtonClicked: () -> Unit
){
    var searchMode by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }

    val chipStates = remember { mutableStateListOf<ChipState>() }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val searchSource = remember { MutableInteractionSource() }

    LaunchedEffect(currentRoute) {
        when (currentRoute) {
            MainScreenUI.CameraScreen.route -> searchMode = false
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
                searchMode = searchMode,
                searchText = searchText,
                searchSource = searchSource,
                onLoginButtonClicked = loginButtonClicked,
                onNotificationButtonClicked = notificationButtonClicked,
                onAddChip = {
                    chipStates.add(ChipState(searchText, mutableStateOf(true)))
                },
                onSearchTextChanged = { searchText = it },
                onSearchTextReset = { searchText = "" },
                onSearchButtonClicked = {
                    searchMode = true
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