package com.ich.reciptopia.presentation.main.components

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ich.reciptopia.presentation.board.components.BoardListScreen
import com.ich.reciptopia.presentation.main.analyze_ingredient.components.AnalyzeIngredientScreen
import com.ich.reciptopia.presentation.main.search.components.SearchScreen

@Composable
fun MainNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    cameraScreen: @Composable () -> Unit,
    searchScreen: @Composable () -> Unit,
    boardScreen: @Composable () -> Unit,
    searchBar: @Composable () -> Unit
){
    Scaffold(
        topBar = {
            searchBar()
        }
    ) {
        NavHost(
            modifier = modifier,
            navController = navController,
            startDestination = MainScreenUI.CameraScreen.route
        ) {
            composable(route = MainScreenUI.CameraScreen.route) {
                cameraScreen()
            }
            composable(route = MainScreenUI.SearchScreen.route) {
                searchScreen()
            }
            composable(route = MainScreenUI.BoardListScreen.route) {
                boardScreen()
            }
        }
    }
}