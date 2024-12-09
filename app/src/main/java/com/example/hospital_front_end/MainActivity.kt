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
import com.example.hospital_front_end.ui.navigation.NavigationViewModel
import com.example.hospital_front_end.ui.theme.HospitalFrontendTheme
import com.example.hospital_front_end.nurseRepository.NurseRepository

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val nurseRepository = NurseRepository()
        val nurseList = nurseRepository.getNurseList()

        setContent {
            HospitalFrontendTheme {
                Surface(
                    modifier = androidx.compose.ui.Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val viewModel = NavigationViewModel(nurseList)
                    Navigation(navController = rememberNavController(), viewModel = viewModel)
                }
            }
        }
    }
}
