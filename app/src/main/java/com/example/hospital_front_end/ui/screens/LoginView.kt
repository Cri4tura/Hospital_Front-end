package com.example.hospital_front_end.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hospital_front_end.R
import coil.compose.AsyncImage

@Composable
fun LoginView(onLoginSuccess: () -> Unit, onBack: () -> Unit) {
    var username by remember { mutableStateOf<String>("") }
    var password by remember { mutableStateOf<String>("") }
    var errorMessage by remember { mutableStateOf<String>("") }

    // Comment when DB connection done
    username = "admin"
    password = "1234"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Hospital Administration",
            style = MaterialTheme.typography.headlineLarge,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 45.dp, top = 100.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // AnimatedGif() --> Prueba de Gift con Coil

        Image(
            painter = painterResource(id = R.drawable.splash),
            contentDescription = "logo image",
            modifier = Modifier
                .width(250.dp)
                .height(250.dp),
            contentScale = ContentScale.FillBounds
        )

        Spacer(modifier = Modifier.height(80.dp))

        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("User", style = MaterialTheme.typography.bodyLarge) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password", style = MaterialTheme.typography.bodyLarge) },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )

        if (errorMessage == "Incorrect credentials") {
            Text(
                text = errorMessage,
                color = Color.Red,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        } else {
            Text(
                text = errorMessage,
                color = Color.Green,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        Button(
            onClick = {
                if (username == "admin" && password == "1234") {
                    errorMessage = "Log In successful"
                    //onLoginSuccess()
                } else {
                    errorMessage = "Incorrect credentials"
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Log In", style = MaterialTheme.typography.bodyLarge)
        }
    }
}


@Composable
fun AnimatedGif() {
    AsyncImage(
        model = R.drawable.logo,
        contentDescription = "GIF animado",
        modifier = Modifier.size(250.dp)
    )
}