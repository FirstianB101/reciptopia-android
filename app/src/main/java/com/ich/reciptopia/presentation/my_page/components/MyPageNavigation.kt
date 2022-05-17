package com.ich.reciptopia.presentation.my_page.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ich.reciptopia.application.ReciptopiaApplication
import com.ich.reciptopia.presentation.my_page.MyPageScreens
import com.ich.reciptopia.presentation.my_page.find_account.FindAccountScreen
import com.ich.reciptopia.presentation.my_page.login.components.LoginScreen
import com.ich.reciptopia.presentation.my_page.profile.ProfileViewModel
import com.ich.reciptopia.presentation.my_page.profile.components.ProfileScreen
import com.ich.reciptopia.presentation.my_page.sign_up.components.SignupScreen

@Composable
fun MyPageNavigation(

){
    val navController = rememberNavController()
    val loginUserState = ReciptopiaApplication.instance?.user?.collectAsState()

    NavHost(
        navController = navController,
        startDestination = if(loginUserState?.value != null) MyPageScreens.MyPageWithLogin.route
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