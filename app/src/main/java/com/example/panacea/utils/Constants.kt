package com.example.panacea.utils

object Constants {

    // SCREENS
    sealed class Screen(val route: String) {
        data object HOME : Screen("home")
        data object LOGIN : Screen("login")
        data object SIGNING : Screen("signIn")
        data object NURSELIST : Screen("nurseList")
        data object DIRECTORY : Screen("agenda")
        data object DETAIL : Screen("detail")
        data object SPLASH : Screen("splashScreen")
        data object PROFILE : Screen("user")
        data object DOCUMENTS : Screen("documents")
        data object NEWS : Screen("news")
        data object HISTORY : Screen("history")
    }

    // NAVIGATION
    sealed class NavigationEvent {
        data object NavigateToHome : NavigationEvent()
        data object NavigateToLogin : NavigationEvent()
        data object NavigateToRegister : NavigationEvent()
        data object NavigateToNurseList : NavigationEvent()
        data object NavigateToDirectory : NavigationEvent()
        data object NavigateBack : NavigationEvent()
        data object NavigateToDetail : NavigationEvent()
        data object NavigateToProfile : NavigationEvent()
        data object NavigateToSplashScreen : NavigationEvent()
        data object NavigateToDocuments : NavigationEvent()
        data object NavigateToNews : NavigationEvent()
        data object NavigateToHistory : NavigationEvent()

    }

    enum class MENU(private val value: String) {
        OPTION_0("0"),
        OPTION_1("1"),
        OPTION_2("2"),
        OPTION_3("3"),
        OPTION_4("4");

        override fun toString(): String {
            return value
        }
    }


    // VARIABLES
    const val DEFAULT_USERNAME = "admin@gmail.com"
    const val DEFAULT_PASSWORD = "1234"


}

