package com.example.hospital_front_end.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.hospital_front_end.model.nurse.Nurse
import com.example.hospital_front_end.ui.screens.home.HomeView
import com.example.hospital_front_end.ui.screens.login.LoginView
import com.example.hospital_front_end.ui.screens.nurseInfo.FindByNameView
import com.example.hospital_front_end.ui.screens.nurseInfo.NurseList
import com.example.hospital_front_end.ui.screens.signIn.SignInView
import com.example.hospital_front_end.utils.Constants

@Composable
fun Navigation(
    navController: NavHostController,
    viewModel: NavigationViewModel
) {

    LaunchedEffect(key1 = viewModel) {
        viewModel.navigationEvent.collect { event ->
            when (event) {
                NavigationEvent.NavigateToHome -> navController.navigate(Constants.Screen.Home.route)
                NavigationEvent.NavigateToLogin -> navController.navigate(Constants.Screen.Login.route)
                NavigationEvent.NavigateToFindByName -> navController.navigate(Constants.Screen.FindByName.route)
                NavigationEvent.NavigateToNurseList -> navController.navigate(Constants.Screen.NurseList.route)
                NavigationEvent.NavigateToRegister -> navController.navigate(Constants.Screen.SignIn.route)
                NavigationEvent.NavigateBack -> navController.popBackStack()
            }
        }
    }

    NavHost(navController = navController, startDestination = Constants.Screen.Login.route) {
        composable(Constants.Screen.Home.route) {
            HomeView(
                onConfirmLogout = { viewModel.navigateToLogin() },
                onViewNurseList = { viewModel.navigateToNurseList() },
                onSearchByName = { viewModel.navigateToFindByName() }
            )
        }
        composable(Constants.Screen.Login.route) {
            LoginView(
                onNavigateToHome = { viewModel.navigateToHome() },
                navigateToSignIn = { viewModel.navigateToSignIn() }
            )
        }
        composable(Constants.Screen.SignIn.route) {
            SignInView(
                onRegister = { name, lastName, birdthDay, email ->
                    viewModel.navigateToHome( )
                },
                onBack = { viewModel.navigateBack() }
            )
        }
        composable(Constants.Screen.NurseList.route) {
            val nurseList by viewModel.nurseList.collectAsState()
            NurseList(
                nurseList = nurseList,
                onBack = { viewModel.navigateBack() }
            )
        }
        composable(Constants.Screen.FindByName.route) {
            val nurseList by viewModel.nurseList.collectAsState()
            FindByNameView(
                nurseList = nurseList,
                onBack = { viewModel.navigateBack() }
            )
        }
    }
}