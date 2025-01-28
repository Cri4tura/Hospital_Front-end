package com.example.panacea.utils

object Constants {

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

