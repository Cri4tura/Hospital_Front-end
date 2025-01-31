package com.example.panacea.ui.screens.profile

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.panacea.R
import com.example.panacea.ui.navigation.HOME
import com.example.panacea.data.models.nurse.Nurse
import com.example.panacea.ui.components.DateInput
import com.example.panacea.ui.components.DeleteAccountButton
import com.example.panacea.ui.components.DrawerAppBar
import com.example.panacea.ui.components.EmailInput
import com.example.panacea.ui.components.LogoutButton
import com.example.panacea.ui.components.PrimaryButton
import com.example.panacea.ui.components.ResetPasswordDialog
import com.example.panacea.ui.components.RoundedImagePicker
import com.example.panacea.ui.components.TextInput
import kotlinx.coroutines.launch

@Composable
fun ProfileView(nav: NavHostController) {
    val context = LocalContext.current
    var name by remember { mutableStateOf("John") }
    var surname by remember { mutableStateOf("Doe") }
    var email by remember { mutableStateOf("") }
    var registerDate by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }

    // Estados para el diálogo de reset de contraseña
    val showPasswordResetDialog = remember { mutableStateOf(false) }
    val newPassword = remember { mutableStateOf("") }
    val confirmPassword = remember { mutableStateOf("") }
    val passwordError = remember { mutableStateOf<String?>(null) }
    var onChangeImageClick by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row (
            verticalAlignment = Alignment.CenterVertically
        ){
            IconButton(
                onClick = {
                    nav.popBackStack()
                }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier.size(35.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            PrimaryButton(
                onClick = {
                    Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show()
                },
                icon = null,
                text = "Save",
                enabled = true,
                description = "Save changes"
            )
        }


        Spacer(modifier = Modifier.height(16.dp))

        RoundedImagePicker(
            imageUri = onChangeImageClick,
            onImageChange = { onChangeImageClick = !onChangeImageClick }
        )

        Spacer(modifier = Modifier.weight(1f))

        TextInput(
            textInput = name,
            onTextChange = { name = it },
            label = "Name",
            isError = null
        )

        TextInput(
            textInput = surname,
            onTextChange = { surname = it },
            label = "Surname",
            isError = null
        )

        EmailInput(
            email = email,
            onEmailChange = { email = it },
            isError = null
        )

        DateInput(
            value = birthDate,
            onValueChange = { birthDate = it },
            label = "Birth Date (DD/MM/YYYY)",
            isError = null
        )

        DateInput(
            value = registerDate,
            onValueChange = { registerDate = it },
            label = "register Date (DD/MM/YYYY)",
            isError = null
        )

        Text(
            text = "Reset Password",
            modifier = Modifier.clickable {
                showPasswordResetDialog.value = true
            }
        )

        Spacer(modifier = Modifier.weight(1f))

        ResetPasswordDialog(
            showPasswordResetDialog = showPasswordResetDialog,
            newPassword = newPassword,
            confirmPassword = confirmPassword,
            context = context,
            passwordError = passwordError
        )

        Spacer(modifier = Modifier.height(16.dp))

        DeleteAccountButton(
            onClick = { /* Implement logout */ },
            text = "Delete Account",
            enabled = true,
        )
    }
}

