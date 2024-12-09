package com.example.hospital_front_end.utils

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Login : Screen("login")
    object SignIn : Screen("signIn")
    object NurseList : Screen("nurseList")
    object FindByName : Screen("findByName")
}