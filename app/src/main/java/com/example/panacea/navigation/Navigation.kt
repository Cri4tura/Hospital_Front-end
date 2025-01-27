package com.example.panacea.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.panacea.ui.screens.home.HomeView
import com.example.panacea.ui.screens.home.HomeViewModel
import com.example.panacea.ui.screens.login.LoginView
import com.example.panacea.ui.screens.login.LoginViewModel
import com.example.panacea.ui.screens.directory.FindByNameView
import com.example.panacea.ui.screens.directory.DirectoryViewModel
import com.example.panacea.ui.screens.directory.NurseList
import com.example.panacea.ui.screens.detail.DetailView
import com.example.panacea.ui.screens.documents.DocsView
import com.example.panacea.ui.screens.history.HistoryView
import com.example.panacea.ui.screens.news.NewsView
import com.example.panacea.ui.screens.profile.ProfileView
import com.example.panacea.ui.screens.signIn.SignInView
import com.example.panacea.ui.screens.splash_screen.SplashScreen
import com.example.panacea.ui.screens.signIn.SignInViewModel
import com.example.panacea.utils.Constants.Screen
import com.example.panacea.utils.Constants.NavigationEvent
import org.koin.compose.koinInject

@Composable
fun Navigation(
    navController: NavHostController,
    navViewModel: NavigationController,
) {
    
    LaunchedEffect(key1 = navViewModel) {
        navViewModel.navigationEvent.collect { event ->
            when (event) {
                NavigationEvent.NavigateToHome -> navController.navigate(Screen.HOME.route)
                NavigationEvent.NavigateToLogin -> navController.navigate(Screen.LOGIN.route)
                NavigationEvent.NavigateToDirectory -> navController.navigate(Screen.DIRECTORY.route)
                NavigationEvent.NavigateToNurseList -> navController.navigate(Screen.NURSELIST.route)
                NavigationEvent.NavigateToRegister -> navController.navigate(Screen.SIGNING.route)
                NavigationEvent.NavigateToDetail -> navController.navigate(Screen.DETAIL.route)
                NavigationEvent.NavigateToSplashScreen -> navController.navigate(Screen.SPLASH.route)
                NavigationEvent.NavigateToProfile -> navController.navigate(Screen.PROFILE.route)
                NavigationEvent.NavigateToDocuments -> navController.navigate(Screen.DOCUMENTS.route)
                NavigationEvent.NavigateToNews -> navController.navigate(Screen.NEWS.route)
                NavigationEvent.NavigateToHistory -> navController.navigate(Screen.HISTORY.route)
                NavigationEvent.NavigateBack -> navController.popBackStack()
            }
        }
    }

    NavHost(navController = navController, startDestination = Screen.SPLASH.route) {
        composable(Screen.SPLASH.route) {
            SplashScreen(navController = navController)
        }
        composable(Screen.HOME.route) {
            HomeView(
                nav = navViewModel,
                vm = HomeViewModel()
            )
        }
        composable(Screen.LOGIN.route) {
            LoginView(
                viewModel = LoginViewModel(),
                onNavigateToHome = { navViewModel.navigateToHome() },
                navigateToSignIn = { navViewModel.navigateToSignIn() }
            )
        }
        composable(Screen.SIGNING.route) {
            SignInView(

                viewModel = SignInViewModel(koinInject()),
                onRegister = { name, lastName, birdthDay, email ->
                    navViewModel.navigateToHome( )
                },
                navViewModel = navViewModel,
                onBack = { navViewModel.navigateBack() }
            )
        }
        composable(Screen.NURSELIST.route) {
            NurseList(
                nav = navViewModel
            )
        }
        composable(Screen.DIRECTORY.route) {
            FindByNameView(
                nav = navViewModel,
                vm = DirectoryViewModel(koinInject()),
            )
        }
        composable(Screen.DETAIL.route) {
            DetailView(
                nurse = navViewModel.selectedNurse,
                onBack = { navViewModel.navigateBack() }
            )
        }
        composable(Screen.PROFILE.route) {
            ProfileView(nav = navViewModel)
        }
        composable(Screen.DOCUMENTS.route) {
            DocsView(nav = navViewModel)
        }
        composable(Screen.NEWS.route) {
            NewsView(nav = navViewModel)
        }
        composable(Screen.HISTORY.route) {
            HistoryView(nav = navViewModel)
        }
    }
}
