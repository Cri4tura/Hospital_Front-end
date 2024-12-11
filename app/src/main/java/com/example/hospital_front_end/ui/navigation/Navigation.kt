package com.example.hospital_front_end.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.hospital_front_end.R
import com.example.hospital_front_end.model.nurse.Nurse
import com.example.hospital_front_end.ui.screens.home.HomeView
import com.example.hospital_front_end.ui.screens.login.LoginView
import com.example.hospital_front_end.ui.screens.login.LoginViewModel
import com.example.hospital_front_end.ui.screens.nurseInfo.FindByNameView
import com.example.hospital_front_end.ui.screens.nurseInfo.NurseList
import com.example.hospital_front_end.ui.screens.profile.ProfileView
import com.example.hospital_front_end.ui.screens.signIn.SignInView
import com.example.hospital_front_end.utils.Constants




@Composable
fun Navigation(
    navController: NavHostController,
    viewModel: NavigationViewModel
) {

    LaunchedEffect(key1 = viewModel) {
        viewModel.navigationEvent.collect { event ->
            when (event) {
                Constants.NavigationEvent.NavigateToHome -> navController.navigate(Constants.Screen.Home.route)
                Constants.NavigationEvent.NavigateToLogin -> navController.navigate(Constants.Screen.Login.route)
                Constants.NavigationEvent.NavigateToFindByName -> navController.navigate(Constants.Screen.FindByName.route)
                Constants.NavigationEvent.NavigateToNurseList -> navController.navigate(Constants.Screen.NurseList.route)
                Constants.NavigationEvent.NavigateToRegister -> navController.navigate(Constants.Screen.SignIn.route)
                Constants.NavigationEvent.NavigateToProfile -> navController.navigate(Constants.Screen.Profile.route)
                Constants.NavigationEvent.NavigateBack -> navController.popBackStack()
            }
        }
    }

    NavHost(navController = navController, startDestination = Constants.Screen.Login.route) {
        composable(Constants.Screen.Home.route) {
            HomeView(
                onConfirmLogout = { viewModel.navigateToLogin() },
                onViewNurseList = { viewModel.navigateToNurseList() },
                onSearchByName = { viewModel.navigateToFindByName() }
            )
        }
        composable(Constants.Screen.Login.route) {
            LoginView(
                viewModel = LoginViewModel(),
                onNavigateToHome = { viewModel.navigateToHome() },
                navigateToSignIn = { viewModel.navigateToSignIn() }
            )
        }
        composable(Constants.Screen.SignIn.route) {
            SignInView(
                onRegister = { name, lastName, birdthDay, email ->
                    viewModel.navigateToHome( )
                },
                onBack = { viewModel.navigateBack() }
            )
        }
        composable(Constants.Screen.NurseList.route) {
            val nurseList by viewModel.nurseList.collectAsState()
            NurseList(
                nurseList = nurseList,
                onBack = { viewModel.navigateBack() },
                navigateToProfile = { nurse ->
                    viewModel.navigateToProfile(nurse)
                }
            )
        }
        composable(Constants.Screen.FindByName.route) {
            val nurseList by viewModel.nurseList.collectAsState()
            FindByNameView(
                nurseList = nurseList,
                onBack = { viewModel.navigateBack() },
                navigateToProfile = { nurse ->
                    viewModel.navigateToProfile(nurse)
                }
            )
        }
        composable(Constants.Screen.Profile.route) {
            ProfileView(
                nurse = viewModel.selectedNurse,
                onBack = { viewModel.navigateBack() }
            )
        }
    }
}

@Composable
fun PrimaryButton(
    onClick: () -> Unit,
    imageResource: Int,
    text: String,
    contentDescription: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
    ) {
        Icon(
            painter = painterResource(id = imageResource),
            contentDescription = contentDescription,
            modifier = Modifier.size(ButtonDefaults.IconSize)
        )
        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
        Text(text, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun LogoutButton(
    onClick: () -> Unit, modifier: Modifier = Modifier, enabled: Boolean, text: String,
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Red.copy(alpha = 0.7f),
            contentColor = Color.Black
        )
    ) {
        Icon(
            painter = painterResource(id = R.drawable.log_out),
            contentDescription = "Log Out",
            modifier = Modifier.size(ButtonDefaults.IconSize)
        )
        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
        Text(text, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun LogoutConfirmationDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text("Confirmation") },
            text = { Text("Are you sure you want to log out?") },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text("No", color = lerp(Color.Green, Color.Black, 0.35f))
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    onDismiss()
                    onConfirm()
                }) {
                    Text(
                        "Yes",
                        modifier = Modifier.padding(horizontal = 40.dp),
                        color = lerp(Color.Red, Color.Black, 0.35f)
                    )
                }
            }
        )
    }
}


@Composable
fun NurseItem(nurse: Nurse, navigateToProfile: (Nurse) -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navigateToProfile(nurse)
            }) {
        Image(
            painter = painterResource(id = R.drawable.user),
            contentDescription = "Nurse Icon", modifier = Modifier.size(60.dp)
        )
        Column(modifier = Modifier.padding(start = 16.dp)) {
            Text(
                text = "${nurse.name} ${nurse.surname}",
                style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = nurse.email,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Cyan
            )
        }
    }
}



@Composable
fun NurseExtendedItem(nurse: Nurse) {

    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(id = R.drawable.user),
            contentDescription = "Nurse Icon", modifier = Modifier.size(120.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = "${nurse.name} ${nurse.surname}",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp
        )
    }

    Spacer(modifier = Modifier.height(20.dp))

    Column {
        Row {
            Text(
                text = "Age: ",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Cyan,
                fontSize = 22.sp
            )
            Text(
                text = nurse.age.toString(),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Cyan,
                fontSize = 22.sp
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
        Row {
            Text(
                text = "Birth Date: ",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Cyan,
                fontSize = 22.sp
            )
            Text(
                text = nurse.formatDate(nurse.birthDate),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Cyan,
                fontSize = 22.sp
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            Text(
                text = "Email: ",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Cyan,
                fontSize = 22.sp
            )
            Text(
                text = nurse.email,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Cyan,
                fontSize = 22.sp
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
        Row {
            Text(
                text = "Register Date: ",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Cyan,
                fontSize = 22.sp
            )
            Text(
                text = nurse.formatDate(nurse.registerDate),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Cyan,
                fontSize = 22.sp
            )
        }
    }
}
