package com.ich.reciptopia.presentation.login.components

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ich.reciptopia.presentation.login.LoginWithSignupScreens

@Composable
fun LoginNavigation(){
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = LoginWithSignupScreens.LoginScreen.route
    ){
        composable(route = LoginWithSignupScreens.LoginScreen.route){
            LoginScreen(navController)
        }
        composable(route = LoginWithSignupScreens.SignupScreen.route){
            SignupScreen(navController)
        }
        composable(route = LoginWithSignupScreens.FindAccountScreen.route){
            FindAccountScreen()
        }
    }
}