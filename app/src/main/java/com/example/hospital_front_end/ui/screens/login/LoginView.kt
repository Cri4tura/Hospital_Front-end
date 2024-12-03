package com.example.hospital_front_end.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hospital_front_end.R
import com.example.hospital_front_end.utils.Constants

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginView(onLoginSuccess: () -> Unit) {
    var username by remember { mutableStateOf<String>("") }
    var password by remember { mutableStateOf<String>("") }
    var errorMessage by remember { mutableStateOf<String>("") }
    var passwordVisible by remember { mutableStateOf(false) }

    // Comment when DB connection done
    username = Constants.USER
    password = Constants.PASS

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .padding(top = 60.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Hospital Administration",
            style = MaterialTheme.typography.headlineLarge,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 45.dp, top = 20.dp)
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

        Spacer(modifier = Modifier.height(40.dp))

        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Email", style = MaterialTheme.typography.bodyLarge) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(15.dp),
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(value = password,
            onValueChange = { password = it },
            label = { Text("Password", style = MaterialTheme.typography.bodyLarge) },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            shape = RoundedCornerShape(15.dp),
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    val icon =
                        if (passwordVisible) R.drawable.visibility_off else R.drawable.visibility
                    val description = if (passwordVisible) "Hide password" else "Show password"
                    Image(
                        modifier = Modifier
                            .width(35.dp)
                            .height(35.dp),
                        painter = painterResource(id = icon),
                        contentDescription = description
                    )
                }
            })

        if (errorMessage == "Incorrect credentials") {
            Text(
                text = errorMessage,
                color = Color.Red,
            )
        } else {
            Text(
                text = errorMessage,
                color = Color.Green,
            )
        }

        Button(
            onClick = {
                if (username == Constants.USER && password == Constants.PASS) {
                    //errorMessage = "Log In successful"
                    onLoginSuccess()
                } else {
                    errorMessage = "Incorrect credentials"
                }
            }, modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                "Log In",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Text(
            "Don't have an account yet?",
            modifier = Modifier
                .align(Alignment.Start)
                .padding(horizontal = 10.dp),
            style = MaterialTheme.typography.bodyMedium
        )
        Button(
            onClick = { /* TODO: Go to RegisterView */ }, modifier = Modifier.fillMaxWidth()

        ) {
            Text(
                "Sign In",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
            )
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}


@Preview(showBackground = true)
@Composable
fun LoginViewPreview() {
    LoginView(onLoginSuccess = { /* Simulated Login Success */ })
}
