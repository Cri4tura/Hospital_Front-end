package com.example.panacea.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.panacea.ui.screens.home.HomeView
import com.example.panacea.ui.screens.home.HomeViewModel
import com.example.panacea.ui.screens.login.LoginView
import com.example.panacea.ui.screens.login.LoginViewModel
import com.example.panacea.ui.screens.directory.FindByNameView
import com.example.panacea.ui.screens.directory.DirectoryViewModel
import com.example.panacea.ui.screens.detail.DetailView
import com.example.panacea.ui.screens.detail.DetailViewModel
import com.example.panacea.ui.screens.documents.DocsView
import com.example.panacea.ui.screens.history.HistoryView
import com.example.panacea.ui.screens.news.NewsView
import com.example.panacea.ui.screens.profile.ProfileView
import com.example.panacea.ui.screens.signIn.SignInView
import com.example.panacea.ui.screens.splash.SplashScreen
import com.example.panacea.ui.screens.signIn.SignInViewModel
import org.koin.compose.koinInject

@Composable
fun Navigation(
    nav: NavHostController
) {

    NavHost(navController = nav, startDestination = SPLASH) {
        composable<SPLASH> {
            SplashScreen(navController = nav)
        }
        composable<HOME> {
            HomeView(
                nav = nav,
                vm = HomeViewModel()
            )
        }
        composable<LOGIN> {
            LoginView(
                viewModel = LoginViewModel(),
                onNavigateToHome = { nav.navigate(HOME) },
                navigateToSignIn = { nav.navigate(SIGNING) }
            )
        }
        composable<SIGNING> {
            SignInView(
                viewModel = SignInViewModel(koinInject()),
                onRegister = { name, lastName, birdthDay, email ->
                    nav.navigate(HOME)
                },
                onBack = { nav.popBackStack() }
            )
        }
        composable<DIRECTORY> {
            FindByNameView(
                nav = nav,
                vm = DirectoryViewModel(koinInject()),
            )
        }
        composable<DETAIL> { backStackEntry ->
            val detail = backStackEntry.toRoute<DETAIL>()
            DetailView(
                nurseId = detail.nurseId,
                nav = nav,
                vm = DetailViewModel(
                    repository = koinInject(),
                    nurseId = detail.nurseId
                )
            )
        }
        composable<PROFILE> {
            ProfileView(nav = nav)
        }
        composable<DOCUMENTS> {
            DocsView(nav = nav)
        }
        composable<NEWS> {
            NewsView(nav = nav)
        }
        composable<HISTORY> {
            HistoryView(nav = nav)
        }
    }
}
