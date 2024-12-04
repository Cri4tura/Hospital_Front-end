package com.example.hospital_front_end.ui.navigation

import androidx.compose.runtime.Composable
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
fun Navigation(navController: NavHostController, nurseList: ArrayList<Nurse>) {

    NavHost(navController = navController, startDestination = Constants.Routes.LOGIN) {
        composable(Constants.Routes.HOME) {
            HomeView(
                onConfirmLogout = { navController.navigate(Constants.Routes.LOGIN) },
                onViewList = { navController.navigate(Constants.Routes.GET_ALL_NURSES) },
                onFindByName = { navController.navigate(Constants.Routes.FIND_BY_NAME) }
            )
        }
        composable(Constants.Routes.LOGIN) {
            LoginView(
                onLoginSuccess = { navController.navigate(Constants.Routes.HOME) },
                goToRegister = { navController.navigate(Constants.Routes.SIGN_IN) }
            )
        }
        composable(Constants.Routes.SIGN_IN) {
            SignInView(
                onRegister = { name, lastName, age, email, registrationDate ->
                    navController.navigate(Constants.Routes.HOME)
                }
            )
        }
        composable(Constants.Routes.GET_ALL_NURSES) {
            NurseList(
                nurseList = nurseList,
                onBack = { navController.popBackStack() }
            )
        }
        composable(Constants.Routes.FIND_BY_NAME) {
            FindByNameView(
                nurseList = nurseList,
                onBack = { navController.popBackStack() }
            )
        }
    }
}