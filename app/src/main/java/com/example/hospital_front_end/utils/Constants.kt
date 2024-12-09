package com.example.hospital_front_end.utils

object Constants {

    // VARIABLES
    const val DEFAULT_USERNAME = "admin@gmail.com"
    const val DEFAULT_PASSWORD = "1234"

    // SCREENS
    sealed class Screen(val route: String) {
        object Home : Screen("home")
        object Login : Screen("login")
        object SignIn : Screen("signIn")
        object NurseList : Screen("nurseList")
        object FindByName : Screen("findByName")
    }

    // NAVIGATION
    sealed class NavigationEvent {
        object NavigateToHome : NavigationEvent()
        object NavigateToLogin : NavigationEvent()
        object NavigateToRegister : NavigationEvent()
        object NavigateToNurseList : NavigationEvent()
        object NavigateToFindByName : NavigationEvent()
        object NavigateBack : NavigationEvent()
    }
}

