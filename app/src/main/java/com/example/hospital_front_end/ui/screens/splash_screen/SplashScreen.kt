package com.example.hospital_front_end.ui.screens.splash_screen

import android.window.SplashScreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.hospital_front_end.R
import com.example.hospital_front_end.ui.screens.login.LoginView
import com.example.hospital_front_end.utils.Constants
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController){
    LaunchedEffect(key1 = true){
        delay(1300)
        navController.popBackStack()
        navController.navigate(Constants.Screen.Login.route)
    }
    Splash()
}

@Composable
fun Splash(){
    Column (modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ){

        Image(
            painter = painterResource(id = R.drawable.spalsh),
            contentDescription = "Nurse Icon", modifier = Modifier.size(500.dp)
        )

        Text(text = "Bienvenido",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold)
    }
}

 @Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SplashScreenPreview() {
     Splash()
 }