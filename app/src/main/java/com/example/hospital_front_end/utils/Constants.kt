package com.example.hospital_front_end.utils

object Constants {

    const val DEFAULT_USERNAME = "admin@gmail.com"
    const val DEFAULT_PASSWORD = "1234"

    sealed class Screen(val route: String) {
        object Home : Screen("home")
        object Login : Screen("login")
        object SignIn : Screen("signIn")
        object NurseList : Screen("nurseList")
        object FindByName : Screen("findByName")
    }
}

