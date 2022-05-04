package com.ich.reciptopia.presentation.login.components

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ich.reciptopia.presentation.login.MyPageScreens

@Composable
fun LoginNavigation(
    logined: Boolean
){
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = if(logined) MyPageScreens.MyPageWithLogin.route
                           else MyPageScreens.MyPageWithoutLogin.route
    ){
        composable(route = MyPageScreens.LoginScreen.route){
            LoginScreen(navController)
        }
        composable(route = MyPageScreens.SignupScreen.route){
            SignupScreen(navController)
        }
        composable(route = MyPageScreens.FindAccountScreen.route){
            FindAccountScreen()
        }
        composable(route = MyPageScreens.MyPageWithoutLogin.route){
            MyPageScreenWithoutLogin(navController)
        }
        composable(route = MyPageScreens.MyPageWithLogin.route){
            MyPageScreenWithLogin(navController)
        }
        composable(route = MyPageScreens.ProfileScreen.route){
            ProfileScreen()
        }
    }
}