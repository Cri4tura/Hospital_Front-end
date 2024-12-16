package com.example.hospital_front_end.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hospital_front_end.R
import com.example.hospital_front_end.ui.components.LogoutButton
import com.example.hospital_front_end.ui.components.LogoutConfirmationDialog
import com.example.hospital_front_end.ui.components.PrimaryButton

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
            painter = painterResource(id = R.drawable.nurse_register),
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


@Preview(showBackground = true)
@Composable
fun HomeViewPreview() {
    HomeView(
        onConfirmLogout = { /* Simulated Confirm Logout */ },
        onViewNurseList = { /* Simulated View List */ },
        onSearchByName = { /* Simulated Find By Name */ }
    )
}
