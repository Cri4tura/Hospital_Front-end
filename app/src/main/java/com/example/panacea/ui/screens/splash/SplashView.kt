package com.example.panacea.ui.screens.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import com.example.panacea.ui.components.Splash
import com.example.panacea.ui.navigation.LOGIN
import kotlinx.coroutines.delay

@Composable
fun SplashView(nav: NavController) {
    LaunchedEffect(key1 = true) {
        delay(200)
        nav.navigate(LOGIN)
    }
    Splash()
}