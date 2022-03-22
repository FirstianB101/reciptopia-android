package com.ich.reciptopia.presentation.login

sealed class LoginWithSignupScreens(val route: String){
    object LoginScreen: LoginWithSignupScreens("login_screen")
    object SignupScreen: LoginWithSignupScreens("signup_screen")
    object FindAccountScreen: LoginWithSignupScreens("find_account_screen")
}
