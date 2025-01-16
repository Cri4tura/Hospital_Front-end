package com.example.hospital_front_end.ui.screens.signIn

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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.hospital_front_end.R
import com.example.hospital_front_end.nurseRepository.NurseRepository
import com.example.hospital_front_end.ui.components.EmailInput
import com.example.hospital_front_end.ui.components.PasswordInput
import com.example.hospital_front_end.navigation.NavigationController
import com.example.hospital_front_end.ui.components.DateInput
import com.example.hospital_front_end.ui.components.TextInput
import org.koin.androidx.compose.get
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
            .padding(8.dp)
            .padding(top = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        IconButton(onClick = onBack, modifier = Modifier.align(Alignment.End)) {
            Icon(imageVector = Icons.Filled.Close, contentDescription = "Back")
        }
        Image(
            painter = painterResource(id = R.drawable.nurse_register),
            contentDescription = "Glide image ",
            modifier = Modifier
                .size(200.dp)
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

        Button(

            onClick = {

                viewModel.signIn(name,
                    lastName,
                    email,
                    selectedDate,
                    password,
                    confirmPassword,
                )

            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Create New Account",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun NurseRegisterScreenPreview() {
    SignInView(
        viewModel = SignInViewModel(get()),
        onRegister = { name, lastName, age, email -> println("Registered: $name $lastName, Age: $age, Email: $email") },
        onBack = { /* Handle back button click */ },
        navViewModel = NavigationController(NurseRepository())
    )
}