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
import com.example.hospital_front_end.ui.screens.nurseInfo.FindByNameView
import com.example.hospital_front_end.ui.screens.nurseInfo.FindByNameViewModel
import com.example.hospital_front_end.ui.screens.nurseInfo.NurseList
import com.example.hospital_front_end.ui.screens.profile.ProfileView
import com.example.hospital_front_end.ui.screens.signIn.SignInView
import com.example.hospital_front_end.ui.screens.splash_screen.SplashScreen
import com.example.hospital_front_end.ui.screens.signIn.SignInViewModel
import com.example.hospital_front_end.utils.Constants
import org.koin.androidx.compose.get
import org.koin.compose.koinInject

@Composable
fun Navigation(
    navController: NavHostController,
    navViewModel: NavigationViewModel,
) {
    
    LaunchedEffect(key1 = navViewModel) {
        navViewModel.navigationEvent.collect { event ->
            when (event) {
                Constants.NavigationEvent.NavigateToHome -> navController.navigate(Constants.Screen.Home.route)
                Constants.NavigationEvent.NavigateToLogin -> navController.navigate(Constants.Screen.Login.route)
                Constants.NavigationEvent.NavigateToFindByName -> navController.navigate(Constants.Screen.FindByName.route)
                Constants.NavigationEvent.NavigateToNurseList -> navController.navigate(Constants.Screen.NurseList.route)
                Constants.NavigationEvent.NavigateToRegister -> navController.navigate(Constants.Screen.SignIn.route)
                Constants.NavigationEvent.NavigateToProfile -> navController.navigate(Constants.Screen.Profile.route)
                Constants.NavigationEvent.NavigateToSplashScreen -> navController.navigate(Constants.Screen.SplashScreen.route)
                Constants.NavigationEvent.NavigateBack -> navController.popBackStack()
            }
        }
    }

    NavHost(navController = navController, startDestination = Constants.Screen.Home.route) {
        composable(Constants.Screen.SplashScreen.route) {
            SplashScreen(navController = navController)
        }
        composable(Constants.Screen.Home.route) {
            HomeView(
                navViewModel = navViewModel,
                vm = HomeViewModel()
            )
        }
        composable(Constants.Screen.Login.route) {
            LoginView(
                viewModel = LoginViewModel(),
                onNavigateToHome = { navViewModel.navigateToHome() },
                navigateToSignIn = { navViewModel.navigateToSignIn() }
            )
        }
        composable(Constants.Screen.SignIn.route) {
            SignInView(

                viewModel = SignInViewModel(koinInject()),
                onRegister = { name, lastName, birdthDay, email ->
                    navViewModel.navigateToHome( )
                },
                navViewModel = navViewModel,
                onBack = { navViewModel.navigateBack() }
            )
        }
        composable(Constants.Screen.NurseList.route) {
            NurseList(
                navViewModel = navViewModel
            )
        }
        composable(Constants.Screen.FindByName.route) {
            val nurseList by navViewModel.nurseList.collectAsState()
            FindByNameView(
                navViewModel = navViewModel,
                findByNameViewModel = FindByNameViewModel(koinInject()),
            )
        }
        composable(Constants.Screen.Profile.route) {
            ProfileView(
                nurse = navViewModel.selectedNurse,
                onBack = { navViewModel.navigateBack() }
            )
        }
    }
}
