package com.ich.reciptopia.presentation.login

sealed class MyPageScreens(val route: String){
    object LoginScreen: MyPageScreens("login_screen")
    object SignupScreen: MyPageScreens("signup_screen")
    object FindAccountScreen: MyPageScreens("find_account_screen")
    object MyPageWithoutLogin: MyPageScreens("my_page_without_login")
    object MyPageWithLogin: MyPageScreens("my_page_with_login")
    object ProfileScreen: MyPageScreens("profile_screen")
}
