package com.example.hospital_front_end.ui.screens.nurseInfo

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import com.example.hospital_front_end.model.nurse.Nurse
import java.util.Date

@Composable
fun NurseList(nurseList: ArrayList<Nurse>, onBack: () -> Unit) {
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

        LazyColumn(
            contentPadding = PaddingValues(bottom = 8.dp)
        ) {
            items(nurseList) { nurse ->

                Row {
                    Image(
                        painter = painterResource(id = R.drawable.user),
                        contentDescription = "Icon",
                        modifier = Modifier
                            .size(40.dp)
                            .align(Alignment.CenterVertically)
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    ) {
                        Row(

                        ) {
                            Text(
                                text = nurse.getName(),
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = nurse.getLastName(),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                        Text(
                            text = "Email: ${nurse.getEmail()}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "Edad: ${nurse.getAge()}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "Fecha: ${nurse.getRegisterDate()}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Button(
                        onClick = onBack,
                    ) {
                        Text("Back")
                    }
                }
            }


        }


    }
}

@Preview(showBackground = true)
@Composable
fun MyNurseListPreview() {
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
    NurseList(nurseList = nurseList, onBack = { /* Simulated Back Action */ })
}