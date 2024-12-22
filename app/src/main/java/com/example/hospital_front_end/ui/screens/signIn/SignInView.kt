package com.example.hospital_front_end.ui.screens.signIn

import android.app.DatePickerDialog
import androidx.compose.ui.unit.dp
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.example.hospital_front_end.R
import com.example.hospital_front_end.ui.components.EmailInput
import com.example.hospital_front_end.ui.components.PasswordInput
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun SignInView(
    viewModel: SignInViewModel,
    onRegister: (name: String, lastName: String, birdthDay: String, email: String) -> Unit,
    onBack: () -> Unit
) {
    val context = LocalContext.current

    var name by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    var confirmPassword by remember { mutableStateOf<String>("") }
    var password by remember { mutableStateOf<String>("") }
    var passwordVisible by remember { mutableStateOf(false) }

    var selectedDate by remember { mutableStateOf("") }
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    val calendar = Calendar.getInstance()

    val nameError by viewModel.nameError
    val lastNameError by viewModel.lastNameError
    val emailError by viewModel.emailError
    val birthDateError by viewModel.birthDateError
    val passwordError by viewModel.passwordError
    val confirmPasswordError by viewModel.confirmPasswordError

    val onDateClick = {
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                calendar.set(year, month, dayOfMonth)
                selectedDate = dateFormat.format(calendar.time)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

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
        IconButton(onClick = onBack, modifier = Modifier.align(Alignment.End)) {
            Icon(imageVector = Icons.Filled.Close, contentDescription = "Back")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Image(
            painter = painterResource(id = R.drawable.nurse_register),
            contentDescription = "Glide image ",
            modifier = Modifier
                .size(200.dp)
                .padding(bottom = 8.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(15.dp),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White,
                unfocusedPlaceholderColor = Color.White
            ),
            label = { Text("Name", style = MaterialTheme.typography.bodyLarge) },
            singleLine = true
        )
        if (viewModel.nameError.value != null) {
            Text(text = viewModel.nameError.value!!, color = Color.Red)
        }
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = lastName,
            onValueChange = { lastName = it },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(15.dp),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
            ),
            label = { Text("Surname", style = MaterialTheme.typography.bodyLarge) },
            singleLine = true
        )
        if (viewModel.lastNameError.value != null) {
            Text(text = viewModel.lastNameError.value!!, color = Color.Red)
        }
        Spacer(modifier = Modifier.height(8.dp))
        EmailInput(
            email = email,
            onEmailChange = { email = it },
            isError = null
        )
        if (viewModel.emailError.value != null) {
            Text(text = viewModel.emailError.value!!, color = Color.Red)
        }
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = selectedDate,
            onValueChange = { selectedDate = it },
            label = {
                Text(
                    "Birth Date (DD/MM/YYYY)", style = MaterialTheme.typography.bodyLarge
                )
            },
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
            ),
            singleLine = true,
            trailingIcon = {
                IconButton(onClick = { onDateClick() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.calendar),
                        contentDescription = "Calendar",
                        modifier = Modifier.size(35.dp)
                    )
                }
            }
        )
        if (viewModel.birthDateError.value != null) {
            Text(text = viewModel.birthDateError.value!!, color = Color.Red)
        }

        Spacer(modifier = Modifier.height(8.dp))

        PasswordInput(
            password = password,
            passwordVisible = passwordVisible,
            onPasswordChange = { password = it },
            onPasswordVisibilityToggle = { passwordVisible = !passwordVisible },
            isError = null
        )
        if (viewModel.passwordError.value != null) {
            Text(text = viewModel.passwordError.value!!, color = Color.Red)
        }
        Spacer(modifier = Modifier.height(8.dp))

        PasswordInput(
            password = confirmPassword,
            passwordVisible = passwordVisible,
            onPasswordChange = { confirmPassword = it },
            onPasswordVisibilityToggle = { passwordVisible = !passwordVisible },
            isError = null
        )
        if (viewModel.confirmPasswordError.value != null) {
            Text(text = viewModel.confirmPasswordError.value!!, color = Color.Red)
        }
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
        viewModel = SignInViewModel(),
        onRegister = { name, lastName, age, email -> println("Registered: $name $lastName, Age: $age, Email: $email") },
        onBack = { /* Handle back button click */ }
    )
}