package com.ich.reciptopia.presentation.my_page.components

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ich.reciptopia.presentation.my_page.MyPageScreens
import com.ich.reciptopia.presentation.my_page.find_account.FindAccountScreen
import com.ich.reciptopia.presentation.my_page.login.components.LoginScreen
import com.ich.reciptopia.presentation.my_page.profile.ProfileScreen
import com.ich.reciptopia.presentation.my_page.sign_up.components.SignupScreen

@Composable
fun MyPageNavigation(
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
            ProfileScreen(navController)
        }
    }
}