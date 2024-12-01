package com.example.hospital_front_end.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.hospital_front_end.R

@Composable
fun NurseList(nurseList: List<String>, onBack: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .padding(top = 40.dp)
            .fillMaxSize()
    ) {
        Text(
            "List of Nurse",
            style = MaterialTheme.typography.headlineLarge,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(28.dp))

        Image(
            painter = painterResource(id = R.drawable.lista),
            contentDescription = "Icon",
            modifier = Modifier
                .size(200.dp)
                .align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(28.dp))

        LazyColumn {
            items(nurseList) { nurse ->
                Text(
                    text = nurse,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(26.dp))

        Button(onClick = onBack, modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text("Back")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyNurseListPreview() {
    val nurseList = listOf(
        "Juan Pérez",
        "María Gómez",
        "Luis Fernández",
        "Ana López",
        "Carlos Díaz",
        "Sofía Ramírez",
        "Diego Herrera",
        "Paula Ortiz",
        "Andrés Castro",
        "Elena Vargas",
        "Miguel Ángel Rodríguez",
        "Natalia Morales"
    )
    NurseList(nurseList = nurseList, onBack = { /* Simulated Back Action */ })
}