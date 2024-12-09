package com.example.hospital_front_end.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hospital_front_end.R

@Composable
fun HomeView(
    onConfirmLogout: () -> Unit, onViewNurseList: () -> Unit, onSearchByName: () -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .padding(top = 60.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            "Menu",
            style = MaterialTheme.typography.headlineLarge,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
        )
        Image(
            painter = painterResource(id = R.drawable.nurse),
            contentDescription = "Home screen illustration of a nurse",
            modifier = Modifier
                .size(200.dp)
                .padding(bottom = 8.dp)
        )

        PrimaryButton(
            onClick = onViewNurseList,
            imageResource = R.drawable.list,
            text = "View List",
            contentDescription = "View List Button",
            modifier = Modifier.fillMaxWidth()
        )

        PrimaryButton(
            onClick = { /* TODO */ },
            imageResource = R.drawable.search_id,
            text = "Find by ID",
            contentDescription = "Find by ID Button",
            modifier = Modifier.fillMaxWidth(),
            enabled = false
        )
        PrimaryButton(
            onClick = onSearchByName,
            imageResource = R.drawable.search,
            text = "Find by Name",
            contentDescription = "Find by Name Button",
            modifier = Modifier.fillMaxWidth()
        )

        PrimaryButton(
            onClick = { /* TODO: Acción del botón */ },
            imageResource = R.drawable.update,
            text = "Update Nurse",
            contentDescription = "Update Nurse Button",
            modifier = Modifier.fillMaxWidth(),
                    enabled = false
        )

        PrimaryButton(
            onClick = { /* TODO: Acción del botón */ },
            imageResource = R.drawable.delete,
            text = "Delete Nurse",
            contentDescription = "Delete Nurse Button",
            modifier = Modifier.fillMaxWidth(),
            enabled = false
        )
        Spacer(modifier = Modifier.weight(1f))

        LogoutButton(
            onClick = { showDialog = true },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            enabled = true,
            text = "Log Out",
        )

        LogoutConfirmationDialog(
            showDialog = showDialog,
            onDismiss = { showDialog = false },
            onConfirm = onConfirmLogout
        )

        Spacer(modifier = Modifier.height(32.dp))
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

@Preview(showBackground = true)
@Composable
fun HomeViewPreview() {
    HomeView(
        onConfirmLogout = { /* Simulated Confirm Logout */ },
        onViewNurseList = { /* Simulated View List */ },
        onSearchByName = { /* Simulated Find By Name */ }
    )
}
