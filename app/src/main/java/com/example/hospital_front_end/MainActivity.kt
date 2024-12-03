package com.example.hospital_front_end

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.compose.rememberNavController
import com.example.hospital_front_end.ui.navigation.Navigation
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
                    val nurseList = listOf(
                        "Juan Pérez", "María Gómez", "Luis Fernández", "Ana López",
                        "Carlos Díaz", "Sofía Ramírez", "Diego Herrera", "Paula Ortiz",
                        "Andrés Castro", "Elena Vargas", "Miguel Ángel Rodríguez", "Natalia Morales",
                        "Natalia Gil"
                    )

                    Navigation(navController = rememberNavController(), nurseList = nurseList)
                }
            }
        }
    }
}
