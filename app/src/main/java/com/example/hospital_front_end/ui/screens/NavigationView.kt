package com.example.hospital_front_end.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
fun NavigationView(
    onConfirmLogout: () -> Unit, onViewList: () -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .padding(top = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Menu",
            style = MaterialTheme.typography.headlineLarge,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Spacer(modifier = Modifier.padding(16.dp))
        Image(
            painter = painterResource(id = R.drawable.nurse),
            contentDescription = "Icon",
            modifier = Modifier
                .size(200.dp)
                .padding(bottom = 8.dp)
        )

        Spacer(modifier = Modifier.weight(0.5f))

        Button(
            onClick = onViewList,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
        ) {
            Image(
                painter = painterResource(id = R.drawable.list),
                contentDescription = "Icon",
                modifier = Modifier.size(35.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("getAll()", modifier = Modifier.weight(1f))
        }

        Button(
            onClick = { /* TODO: Acción del botón */ },
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
        ) {
            Image(
                painter = painterResource(id = R.drawable.search_id),
                contentDescription = "Icon",
                modifier = Modifier.size(35.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("findById()", modifier = Modifier.weight(1f))
        }

        Button(
            onClick = { /* TODO: Acción del botón */ },
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
        ) {
            Image(
                painter = painterResource(id = R.drawable.search),
                contentDescription = "Icon",
                modifier = Modifier.size(35.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("findByName()", modifier = Modifier.weight(1f))
        }

        Button(
            onClick = { /* TODO: Acción del botón */ },
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
        ) {
            Image(
                painter = painterResource(id = R.drawable.update),
                contentDescription = "Icon",
                modifier = Modifier.size(35.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("updateNurse()", modifier = Modifier.weight(1f))
        }

        Button(
            onClick = { /* TODO: Acción del botón */ },
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
        ) {

            Image(
                painter = painterResource(id = R.drawable.delete),
                contentDescription = "Icon",
                modifier = Modifier.size(35.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("deleteById()", modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { showDialog = true },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = lerp(Color.Red, Color.Black, 0.35f), contentColor = Color.White
            )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
            ) {

                Image(
                    painter = painterResource(id = R.drawable.log_out),
                    contentDescription = "Icon",
                    modifier = Modifier.size(35.dp)
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    "Log Out",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.weight(1.9f),
                    fontWeight = FontWeight.Bold,
                )


            }
        }
        if (showDialog) {
            AlertDialog(onDismissRequest = { showDialog = false },
                title = { Text("Confirmation") },
                text = { Text("Are you sure you want to log out??") },
                dismissButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text(
                            "No",

                            color = lerp(Color.Green, Color.Black, 0.35f)
                        )
                    }
                },
                confirmButton = {
                    TextButton(onClick = {
                        showDialog = false
                        onConfirmLogout()
                    }) {
                        Text(
                            "Yes",
                            modifier = Modifier.padding(horizontal = 40.dp),
                            color = lerp(Color.Red, Color.Black, 0.35f)
                        )
                    }
                })
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun NavigationViewPreview() {
    NavigationView(onConfirmLogout = { /* Simulated Confirm Logout */ },
        onViewList = { /* Simulated View List */ })
}