package com.example.panacea.ui.screens.signIn

import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.example.panacea.R
import com.example.panacea.ui.components.EmailInput
import com.example.panacea.ui.components.PasswordInput
import com.example.panacea.navigation.NavigationController
import com.example.panacea.ui.components.DateInput
import com.example.panacea.ui.components.PrimaryButton
import com.example.panacea.ui.components.TextInput
import org.koin.androidx.compose.getViewModel

@Composable
fun SignInView(
    viewModel: SignInViewModel = getViewModel(),
    navViewModel: NavigationController,
    onRegister: (name: String, lastName: String, birdthDay: String, email: String) -> Unit,
    onBack: () -> Unit
) {
    val context = LocalContext.current

    var name by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf("") }
    val isAuthenticated by viewModel.authenticationState.collectAsState()

    LaunchedEffect(isAuthenticated) {
        if (isAuthenticated) {
            onRegister(name, lastName, selectedDate, email)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(
            onClick = onBack,
            modifier = Modifier.align(Alignment.Start)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier.size(35.dp),
                tint = MaterialTheme.colorScheme.primary
            )
        }
        Image(
            painter = painterResource(id = R.drawable.nurse_register),
            contentDescription = "Glide image ",
            modifier = Modifier.size(150.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))

        TextInput(
            textInput = name,
            onTextChange = { name = it },
            label = "Name",
            isError = viewModel.nameError.value
        )

        TextInput(
            textInput = lastName,
            onTextChange = { lastName = it },
            label = "Surname",
            isError = viewModel.surnameError.value
        )

        EmailInput(
            email = email,
            onEmailChange = { email = it },
            isError = viewModel.emailError.value
        )

        DateInput(
            context = context,
            value = selectedDate,
            onValueChange = { selectedDate = it },
            label = "Birth Date (DD/MM/YYYY)",
            isError = viewModel.birthDateError.value
        )

        PasswordInput(
            password = password,
            passwordVisible = passwordVisible,
            onPasswordChange = { password = it },
            onPasswordVisibilityToggle = { passwordVisible = !passwordVisible },
            isError = viewModel.passwordError.value,
        )

        PasswordInput(
            password = confirmPassword,
            passwordVisible = passwordVisible,
            onPasswordChange = { confirmPassword = it },
            onPasswordVisibilityToggle = { passwordVisible = !passwordVisible },
            isError = viewModel.passwordError.value,
        )

        Spacer(modifier = Modifier.weight(1f))

        PrimaryButton(
            text = "Create New Account",
            onClick = {
                viewModel.signIn(
                    name,
                    lastName,
                    email,
                    selectedDate,
                    password,
                    confirmPassword,
                )
            },
            modifier = Modifier.fillMaxWidth(),
            description = "Create New Account",
            enabled = true,
            icon = null
        )

        Spacer(modifier = Modifier.height(32.dp))
    }
}