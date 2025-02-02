package com.example.panacea.ui.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.panacea.R
import com.example.panacea.ui.components.PrimaryButton
import com.example.panacea.ui.components.Splash
import com.example.panacea.ui.navigation.LOGIN
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

//@Composable
//fun AppScreen(nav: NavController) {
//    LaunchedEffect(key1 = true) {
//        delay(200)
//        nav.navigate(LOGIN)
//    }
//    Splash()
//}


@Composable
fun SplashView(nav: NavController, vm: NetworkViewModel = koinViewModel()) {
    // Observar la variable connectionError para ver si hay un error de conexión
    val connectionError by vm.connectionError.observeAsState()
    val isLoading by vm.isLoading.observeAsState(false)

    // Usar LaunchedEffect para navegar solo después de que la conexión haya sido procesada
    LaunchedEffect(connectionError, isLoading) {
        // Navegar solo cuando ya se haya determinado que hay error o que la carga terminó
        if (connectionError == null && !isLoading) {
            // Solo navegar al login si la conexión es exitosa
            delay(1000)
            nav.navigate(LOGIN)
        }
    }

    // Si connectionError es null, significa que no hay error de conexión
    if (connectionError != null) {
        // Mostrar la pantalla de desconexión cuando hay error de conexión
        Splash()
        DisconnectionScreen(onRetry = { vm.onReconnect() }, errorMessage = connectionError)
    } else {
        if (isLoading) {
            // Mostrar la pantalla principal cuando no hay error de conexión
            Splash()
            MainScreen() // O la pantalla de inicio que prefieras
        } else {
            // Mostrar la pantalla principal cuando no hay error de conexión
            Splash()
            //MainScreen() // O la pantalla de inicio que prefieras
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
        Text(text = "Conectado al servidor", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(32.dp))
        Box(
            modifier = Modifier.fillMaxWidth().height(45.dp),
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
            modifier = Modifier.fillMaxWidth().height(45.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            if (isLoading) {
                CircularProgressIndicator()
            } else {
                // Llamar al callback para reintentar

                PrimaryButton(
                    onClick = { onRetry() },
                    icon = Icons.Outlined.Refresh,
                    text = "Reintentar",
                    description = "refresh button"
                )
            }
        }


        Spacer(modifier = Modifier.height(32.dp))
    }

}
