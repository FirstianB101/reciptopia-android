package com.ich.reciptopia.presentation.login

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ich.reciptopia.presentation.login.components.FindAccountScreen
import com.ich.reciptopia.presentation.login.components.LoginScreen
import com.ich.reciptopia.presentation.login.components.SignupScreen

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
            SignupScreen()
        }
        composable(route = LoginWithSignupScreens.FindAccountScreen.route){
            FindAccountScreen()
        }
    }
}