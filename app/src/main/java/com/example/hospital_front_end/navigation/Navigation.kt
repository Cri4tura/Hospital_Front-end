package com.example.hospital_front_end.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.hospital_front_end.ui.screens.home.HomeView
import com.example.hospital_front_end.ui.screens.home.HomeViewModel
import com.example.hospital_front_end.ui.screens.login.LoginView
import com.example.hospital_front_end.ui.screens.login.LoginViewModel
import com.example.hospital_front_end.ui.screens.directory.FindByNameView
import com.example.hospital_front_end.ui.screens.directory.DirectoryViewModel
import com.example.hospital_front_end.ui.screens.directory.NurseList
import com.example.hospital_front_end.ui.screens.detail.DetailView
import com.example.hospital_front_end.ui.screens.documents.DocsView
import com.example.hospital_front_end.ui.screens.history.HistoryView
import com.example.hospital_front_end.ui.screens.news.NewsView
import com.example.hospital_front_end.ui.screens.profile.ProfileView
import com.example.hospital_front_end.ui.screens.signIn.SignInView
import com.example.hospital_front_end.ui.screens.splash_screen.SplashScreen
import com.example.hospital_front_end.ui.screens.signIn.SignInViewModel
import com.example.hospital_front_end.utils.Constants
import org.koin.compose.koinInject

@Composable
fun Navigation(
    navController: NavHostController,
    navViewModel: NavigationController,
) {
    
    LaunchedEffect(key1 = navViewModel) {
        navViewModel.navigationEvent.collect { event ->
            when (event) {
                Constants.NavigationEvent.NavigateToHome -> navController.navigate(Constants.Screen.HOME.route)
                Constants.NavigationEvent.NavigateToLogin -> navController.navigate(Constants.Screen.LOGIN.route)
                Constants.NavigationEvent.NavigateToDirectory -> navController.navigate(Constants.Screen.DIRECTORY.route)
                Constants.NavigationEvent.NavigateToNurseList -> navController.navigate(Constants.Screen.NURSELIST.route)
                Constants.NavigationEvent.NavigateToRegister -> navController.navigate(Constants.Screen.SIGNING.route)
                Constants.NavigationEvent.NavigateToDetail -> navController.navigate(Constants.Screen.DETAIL.route)
                Constants.NavigationEvent.NavigateToSplashScreen -> navController.navigate(Constants.Screen.SPLASH.route)
                Constants.NavigationEvent.NavigateToProfile -> navController.navigate(Constants.Screen.PROFILE.route)
                Constants.NavigationEvent.NavigateToDocuments -> navController.navigate(Constants.Screen.DOCUMENTS.route)
                Constants.NavigationEvent.NavigateToNews -> navController.navigate(Constants.Screen.NEWS.route)
                Constants.NavigationEvent.NavigateToHistory -> navController.navigate(Constants.Screen.HISTORY.route)
                Constants.NavigationEvent.NavigateBack -> navController.popBackStack()
            }
        }
    }

    NavHost(navController = navController, startDestination = Constants.Screen.HOME.route) {
        composable(Constants.Screen.SPLASH.route) {
            SplashScreen(navController = navController)
        }
        composable(Constants.Screen.HOME.route) {
            HomeView(
                nav = navViewModel,
                vm = HomeViewModel()
            )
        }
        composable(Constants.Screen.LOGIN.route) {
            LoginView(
                viewModel = LoginViewModel(),
                onNavigateToHome = { navViewModel.navigateToHome() },
                navigateToSignIn = { navViewModel.navigateToSignIn() }
            )
        }
        composable(Constants.Screen.SIGNING.route) {
            SignInView(

                viewModel = SignInViewModel(koinInject()),
                onRegister = { name, lastName, birdthDay, email ->
                    navViewModel.navigateToHome( )
                },
                navViewModel = navViewModel,
                onBack = { navViewModel.navigateBack() }
            )
        }
        composable(Constants.Screen.NURSELIST.route) {
            NurseList(
                nav = navViewModel
            )
        }
        composable(Constants.Screen.DIRECTORY.route) {
            val nurseList by navViewModel.nurseList.collectAsState()
            FindByNameView(
                nav = navViewModel,
                vm = DirectoryViewModel(koinInject()),
            )
        }
        composable(Constants.Screen.DETAIL.route) {
            DetailView(
                nurse = navViewModel.selectedNurse,
                onBack = { navViewModel.navigateBack() }
            )
        }
        composable(Constants.Screen.PROFILE.route) {
            ProfileView(nav = navViewModel)
        }
        composable(Constants.Screen.DOCUMENTS.route) {
            DocsView(nav = navViewModel)
        }
        composable(Constants.Screen.NEWS.route) {
            NewsView(nav = navViewModel)
        }
        composable(Constants.Screen.HISTORY.route) {
            HistoryView(nav = navViewModel)
        }
    }
}
