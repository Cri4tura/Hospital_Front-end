package com.example.panacea.ui.screens.profile

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.panacea.ui.components.DateInput
import com.example.panacea.ui.components.DeleteAccountButton
import com.example.panacea.ui.components.EmailInput
import com.example.panacea.ui.components.PrimaryButton
import com.example.panacea.ui.components.ResetPasswordDialog
import com.example.panacea.ui.components.RoundedImagePicker
import com.example.panacea.ui.components.TextInput


@Composable
fun ProfileView(
    nav: NavHostController,
    vm: ProfileViewModel
) {
    val context = LocalContext.current
    var name by remember { mutableStateOf(vm.data.currentUser?.name ?: "") }
    var surname by remember { mutableStateOf(vm.data.currentUser?.surname ?: "") }
    var email by remember { mutableStateOf(vm.data.currentUser?.email ?: "") }
    var birthDate by remember { mutableStateOf("") }

    // Estados para el diálogo de reset de contraseña
    val showPasswordResetDialog = remember { mutableStateOf(false) }
    val newPassword = remember { mutableStateOf("") }
    val confirmPassword = remember { mutableStateOf("") }
    val passwordError = remember { mutableStateOf<String?>(null) }
    var onChangeImageClick by remember { mutableStateOf(true) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
        ) {
            if (vm.state.isLoading) {
                LinearProgressIndicator(
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
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
                icon = Icons.Outlined.Create,
                text = "Save",
                enabled = true,
                description = "Save changes"
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Column (
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                RoundedImagePicker(
                    imageUri = onChangeImageClick,
                    onImageChange = { onChangeImageClick = !onChangeImageClick }
                )

                Spacer(Modifier.height(16.dp))
                Text(
                    text = "Reset Password",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.clickable {
                        showPasswordResetDialog.value = true
                    }
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Column (
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                TextInput(
                    textInput = name,
                    onTextChange = { name = it },
                    label = vm.data.currentUser?.name,
                    placeholder = null,
                    isError = null
                )

                TextInput(
                    textInput = surname,
                    onTextChange = { surname = it },
                    label = vm.data.currentUser?.surname,
                    placeholder = null,
                    isError = null
                )

                EmailInput(
                    email = email,
                    onEmailChange = { email = it },
                    label = vm.data.currentUser?.email,
                    placeholder = null,
                    isError = null
                )

                DateInput(
                    value = birthDate,
                    onValueChange = { birthDate = it },
                    label = vm.data.currentUser?.formatDate(vm.data.currentUser?.birthDate),
                    placeholder = null,
                    isError = null
                )


            }
        }
        Spacer(modifier = Modifier.weight(1f))

        Box (
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
            contentAlignment = Alignment.BottomCenter
        ){
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
}

