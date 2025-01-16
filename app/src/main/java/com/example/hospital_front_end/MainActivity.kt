package com.example.hospital_front_end

import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.compose.rememberNavController
import com.example.hospital_front_end.nurseRepository.NurseRepository
import com.example.hospital_front_end.navigation.Navigation
import com.example.hospital_front_end.navigation.NavigationController
import com.example.hospital_front_end.ui.theme.HospitalFrontendTheme

class MainActivity : AppCompatActivity() {
    
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HospitalFrontendTheme {
                Surface(
                    modifier = androidx.compose.ui.Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val viewModel = NavigationController(NurseRepository())
                    Navigation(navController = rememberNavController(), navViewModel = viewModel)
                }
            }
        }
    }
}

