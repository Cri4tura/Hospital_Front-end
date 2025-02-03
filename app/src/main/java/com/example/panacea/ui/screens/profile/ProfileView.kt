package com.example.panacea.ui.screens.profile

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import coil3.compose.rememberAsyncImagePainter
import com.example.panacea.R
import com.example.panacea.domain.models.nurse.Nurse
import com.example.panacea.ui.components.DateInput
import com.example.panacea.ui.components.DeleteAccountButton
import com.example.panacea.ui.components.EmailInput
import com.example.panacea.ui.components.PrimaryButton
import com.example.panacea.ui.components.ResetPasswordDialog
import com.example.panacea.ui.components.TextInput
import com.example.panacea.ui.navigation.LOGIN
import com.example.panacea.ui.navigation.PROFILE
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun ProfileView(
    nav: NavHostController,
    vm: ProfileViewModel
) {
    val context = LocalContext.current

    var name by remember { mutableStateOf("") }
    var surname by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }

    // Estados
    val showPasswordResetDialog = remember { mutableStateOf(false) }
    val newPassword = remember { mutableStateOf("") }
    val confirmPassword = remember { mutableStateOf("") }
    val passwordError = remember { mutableStateOf<String?>(null) }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val uriToDisplay = selectedImageUri ?: R.drawable.fake_profile_img

    // Configuramos el lanzador para abrir el selector de fotos
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri // Guardamos la URI seleccionada
    }

    // Usar LaunchedEffect para observar cambios en el estado
    LaunchedEffect(vm.state.isDeleted) {
        if (vm.state.isDeleted) {
            Toast.makeText(context, "Account Deleted...", Toast.LENGTH_SHORT).show()
            delay(800)
            nav.navigate(LOGIN)
        }
    }

    LaunchedEffect(vm.state.isUpdated) {
        if (vm.state.isUpdated) {
            Toast.makeText(context, "Account Updated...", Toast.LENGTH_SHORT).show()
            nav.popBackStack()
            nav.navigate(PROFILE)
        }
    }

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

            DeleteAccountButton(
                onClick = {
                    vm.data.currentUser?.id?.let {
                        Toast.makeText(context, "Deleting Account...", Toast.LENGTH_SHORT).show()
                        vm.deleteAccount(it)
                    }
                },
                text = "",
                enabled = !vm.state.isLoading,
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier.wrapContentSize()
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(240.dp)
                            .clip(CircleShape)
                            .background(
                                lerp(
                                    MaterialTheme.colorScheme.onPrimary,
                                    MaterialTheme.colorScheme.primary,
                                    0.35f
                                )
                            )
                            .border(3.dp, MaterialTheme.colorScheme.primary, CircleShape)
                    ) {
                        Image(
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop,   // Recortar para llenar el círculo
                            painter = rememberAsyncImagePainter(model = uriToDisplay),
                            contentDescription = null,
                        )
                    }

                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .zIndex(1f) // Asegura que esté adelante
                    ) {
                        IconButton(
                            onClick = {
                                photoPickerLauncher.launch("image/*")
                                vm.changeImage()
                            },
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primary)
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Edit,
                                contentDescription = "Change Image",
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }
                }

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
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
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
                    label = vm.data.currentUser?.dateToString(vm.data.currentUser?.birthDate),
                    placeholder = null,
                    isError = null
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            ResetPasswordDialog(
                showPasswordResetDialog = showPasswordResetDialog,
                newPassword = newPassword,
                confirmPassword = confirmPassword,
                context = context,
                passwordError = passwordError
            )

            Spacer(modifier = Modifier.height(16.dp))

            PrimaryButton(
                onClick = {

                    val dateFormat = SimpleDateFormat(
                        "dd/MM/yyyy",
                        Locale.getDefault()
                    )  // Asegúrate de que el formato coincida con el input
                    val updatedNurse = vm.data.currentUser?.let {
                        val parsedBirthDate = try {
                            if (birthDate.isNotBlank()) dateFormat.parse(birthDate) else it.birthDate
                        } catch (e: Exception) {
                            println("Error al convertir la fecha: ${e.message}")
                            it.birthDate  // Si hay un error, se mantiene la fecha actual
                        }

                        Nurse(
                            id = it.id,
                            name = name.ifBlank { it.name },
                            surname = surname.ifBlank { it.surname },
                            email = email.ifBlank { it.email },
                            password = newPassword.value.ifBlank { it.password },
                            birthDate = parsedBirthDate!!,
                            registerDate = it.registerDate
                        )
                    }

                    println(updatedNurse)
                    if (updatedNurse != null) {
                        vm.updateNurse(updatedNurse)
                    }
                },
                icon = Icons.Outlined.Create,
                text = "Save",
                enabled = !vm.state.isLoading,
                description = "Save changes"
            )
        }
    }
}