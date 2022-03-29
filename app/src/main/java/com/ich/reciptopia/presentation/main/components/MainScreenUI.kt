package com.ich.reciptopia.presentation.main.components

sealed class MainScreenUI(val route: String){
    object CameraScreen: MainScreenUI("camera_screen")
    object SearchScreen: MainScreenUI("search_screen")
    object BoardListScreen: MainScreenUI("boardlist_screen")
}
