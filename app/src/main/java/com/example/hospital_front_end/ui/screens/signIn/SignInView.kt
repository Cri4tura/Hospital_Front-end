package com.example.hospital_front_end.ui.screens.signIn

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.ui.unit.dp
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.hospital_front_end.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInView(
    onRegister: (name: String, lastName: String, birdthDay: String, email: String) -> Unit,
    onBack: () -> Unit
) {
    val context = LocalContext.current

    var name by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    var confirmPassword by remember { mutableStateOf<String>("") }
    var password by remember { mutableStateOf<String>("") }
    var passwordVisible by remember { mutableStateOf(false) }

    var selectedDate by remember { mutableStateOf("") }
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    val calendar = Calendar.getInstance()

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


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .padding(top = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = { onBack() }, colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            ), modifier = Modifier.align(Alignment.End)

        ) {
            Image(
                painter = painterResource(id = R.drawable.close),
                contentDescription = "Back",
                modifier = Modifier.size(32.dp)
            )
        }
        Text(
            "New Account",
            style = MaterialTheme.typography.headlineLarge,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
        )
        Image(
            painter = painterResource(id = R.drawable.id_card),
            contentDescription = "Icon",
            modifier = Modifier
                .size(200.dp)
                .padding(bottom = 8.dp)
        )
        Spacer(modifier = Modifier.weight(0.5f))
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(15.dp),
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            label = { Text("Name", style = MaterialTheme.typography.bodyLarge) },
            singleLine = true
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = lastName,
            onValueChange = { lastName = it },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(15.dp),
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            label = { Text("Surname", style = MaterialTheme.typography.bodyLarge) },
            singleLine = true
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email", style = MaterialTheme.typography.bodyLarge) },
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = selectedDate,
            onValueChange = { selectedDate = it },
            label = {
                Text(
                    "Birth Date (DD-MM-YYYY)", style = MaterialTheme.typography.bodyLarge
                )
            },
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            singleLine = true,
            trailingIcon = {
                IconButton(onClick = { onDateClick() }) {
                    Image(
                        modifier = Modifier
                            .width(35.dp)
                            .height(35.dp),
                        painter = painterResource(R.drawable.calendar),
                        contentDescription = "Calendar"
                    )
                }
            })
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = password,
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
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirm Password", style = MaterialTheme.typography.bodyLarge) },
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
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = {

                if (name.isNotEmpty() && lastName.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty() && selectedDate.isNotEmpty()) {
                    if (password == confirmPassword) {
                        Toast.makeText(context, "Account created successfully", Toast.LENGTH_SHORT)
                            .show()

                        onRegister(
                            name, lastName, selectedDate, email
                        )
                    } else {
                        Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(context, "Please complete all fields", Toast.LENGTH_SHORT).show()
                }
            }, modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Create Account",
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
    SignInView(onRegister = { name, lastName, age, email ->
        println("Registered: $name $lastName, Age: $age, Email: $email")
    }, onBack = {
        println("Back action triggered")
    })
}