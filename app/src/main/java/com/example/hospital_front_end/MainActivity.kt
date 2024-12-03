package com.example.hospital_front_end

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.hospital_front_end.ui.screens.login.LoginView
import com.example.hospital_front_end.ui.screens.navigation.NavigationView
import com.example.hospital_front_end.ui.screens.nurseInfo.FindByNameView
import com.example.hospital_front_end.ui.screens.nurseInfo.NurseList
import com.example.hospital_front_end.ui.theme.HospitalFrontendTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HospitalFrontendTheme {
                Surface(
                    modifier = androidx.compose.ui.Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Box {
                        MainScreen()
                    }
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    val nurseList = listOf(
        "Juan Pérez",
        "María Gómez",
        "Luis Fernández",
        "Ana López",
        "Carlos Díaz",
        "Sofía Ramírez",
        "Diego Herrera",
        "Paula Ortiz",
        "Andrés Castro",
        "Elena Vargas",
        "Miguel Ángel Rodríguez",
        "Natalia Morales",
        "Natalia Gil"
    )

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("navegacion") {
            NavigationView(
                onConfirmLogout = { navController.navigate("login") },
                onViewList = { navController.navigate("list") },
                onFindByName = { navController.navigate("findByName") }
            )
        }
        composable("login") {
            LoginView(onLoginSuccess = { navController.navigate("navegacion") })
        }
        composable("list") {
            NurseList(nurseList = nurseList, onBack = { navController.popBackStack() })
        }
        composable("findByName") {
            FindByNameView(nurseList = nurseList, onBack = { navController.popBackStack() })
        }
    }
}