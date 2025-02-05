package com.example.panacea.ui.screens.splash

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.panacea.ui.components.PrimaryButton
import com.example.panacea.ui.components.Splash
import com.example.panacea.ui.navigation.LOGIN
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

@Composable
fun SplashView(
    nav: NavController,
    vm: SplashViewModel = koinViewModel()
) {
    LaunchedEffect(Unit) {
        vm.fetchDataFromServer()
    }

    Splash()

    when {
        vm.state.isLoading -> {
            MainScreen()
        }
        vm.state.onSuccess -> {
            nav.navigate(LOGIN)
        }
        vm.state.onError -> {
            DisconnectionScreen(
                onRetry = { vm.fetchDataFromServer() },
                errorMessage = "Server disconnected"
            )
        }
    }
}

@Composable
fun MainScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Text(text = "Connecting to server", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(32.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(45.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            CircularProgressIndicator()
        }
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun DisconnectionScreen(onRetry: () -> Unit, errorMessage: String? = null) {

    val isLoading by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.weight(1f))
        errorMessage?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.titleLarge
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(45.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            if (isLoading) {
                CircularProgressIndicator()
            } else {
                // Llamar al callback para reintentar

                PrimaryButton(
                    onClick = { onRetry() },
                    icon = Icons.Outlined.Refresh,
                    text = "Try again",
                    description = "refresh button"
                )
            }
        }


        Spacer(modifier = Modifier.height(32.dp))
    }

}
