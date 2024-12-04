package com.example.hospital_front_end.ui.screens.nurseInfo

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hospital_front_end.R
import com.example.hospital_front_end.model.nurse.Nurse
import java.util.Date

@Composable
fun FindByNameView(nurseList: ArrayList<Nurse>, onBack: () -> Unit) {
    var searchList by remember { mutableStateOf("") }
    var searchResults by remember { mutableStateOf(listOf<Nurse>()) }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .padding(top = 40.dp)
    ) {
        Text(
            "Find nurse by name",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier
                .padding(bottom = 8.dp)
                .align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(28.dp))

        Image(
            painter = painterResource(id = R.drawable.find),
            contentDescription = "Icon",
            modifier = Modifier
                .size(200.dp)
                .align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(28.dp))

        TextField(
            value = searchList,
            onValueChange = { query ->
                searchList = query
                searchResults = nurseList.filter { nurse ->
                    nurse.getName().contains(query, ignoreCase = true) || nurse.getLastName()
                        .contains(query, ignoreCase = true)
                }
            },

            label = { Text("Insert nurse name", style = MaterialTheme.typography.bodyLarge) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(22.dp))

        if (searchResults.isNotEmpty()) {
            Text(
                "Results:",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            searchResults.forEach { nurse ->
                Text(
                    text = nurse.getName(),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(vertical = 2.dp)
                )
            }
        } else if (searchList.isNotEmpty()) {
            Text("No results found", style = MaterialTheme.typography.bodyLarge)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onBack, modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text("Back", style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FindByNameViewPreview() {
    val nurseList = arrayListOf(
        Nurse(1, "Juan", "Pérez", 30, "juan.perez@example.com", Date()),
        Nurse(2, "María", "Gómez", 28, "maria.gomez@example.com", Date()),
        Nurse(3, "Luis", "Fernández", 35, "luis.fernandez@example.com", Date()),
        Nurse(4, "Ana", "López", 40, "ana.lopez@example.com", Date()),
        Nurse(5, "Carlos", "Díaz", 29, "carlos.diaz@example.com", Date()),
        Nurse(6, "Sofía", "Ramírez", 32, "sofia.ramirez@example.com", Date()),
        Nurse(7, "Diego", "Herrera", 27, "diego.herrera@example.com", Date()),
        Nurse(8, "Paula", "Ortiz", 33, "paula.ortiz@example.com", Date()),
        Nurse(9, "Andrés", "Castro", 31, "andres.castro@example.com", Date()),
        Nurse(10, "Elena", "Vargas", 25, "elena.vargas@example.com", Date())
    )
    FindByNameView(nurseList = nurseList, onBack = { /* Simulated Back Action */ })
}