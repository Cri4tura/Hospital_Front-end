package com.example.hospital_front_end.ui.screens.nurseInfo

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hospital_front_end.R
import com.example.hospital_front_end.model.nurse.Nurse
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FindByNameView(nurseList: ArrayList<Nurse>, onBack: () -> Unit) {
    var searchList by remember { mutableStateOf("") }
    var searchResults by remember { mutableStateOf(listOf<Nurse>()) }

    Column(
        modifier = Modifier
            .padding(8.dp)
            .padding(top = 60.dp)
    ) {
        Button(
            onClick = { onBack() }, colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            ), modifier = Modifier.align(Alignment.End)

        ) {
            Image(
                painter = painterResource(id = R.drawable.close),
                contentDescription = "Back",
                modifier = Modifier.size(32.dp)
            )
        }
        Row(
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {

            Image(
                painter = painterResource(id = R.drawable.search),
                contentDescription = "Icon",
                modifier = Modifier.size(45.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                "Find nurse by name",
                style = MaterialTheme.typography.headlineLarge,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }


        Spacer(modifier = Modifier.height(28.dp))

        OutlinedTextField(
            value = searchList,
            onValueChange = { query ->
                searchList = query
                searchResults = nurseList.filter { nurse ->
                    nurse.getName().contains(query, ignoreCase = true) || nurse.getLastName()
                        .contains(query, ignoreCase = true)
                }
            },

            label = { Text("Insert nurse name", style = MaterialTheme.typography.bodyLarge) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(15.dp),
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(22.dp))

        if (searchResults.isNotEmpty()) {
            Text(
                "Results:",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            LazyColumn(
                contentPadding = PaddingValues(bottom = 8.dp),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                items(searchResults) { nurse ->
                    Row(
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.user),
                            contentDescription = "Icon",
                            modifier = Modifier
                                .size(80.dp)
                                .align(Alignment.CenterVertically)
                        )
                        Column(
                            modifier = Modifier
                                .padding(start = 25.dp)
                                .align(Alignment.CenterVertically)
                        ) {
                            Row {
                                Text(
                                    text = nurse.getName(),
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp
                                )
                                Text(
                                    text = nurse.getLastName(),
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = nurse.getEmail(),
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Cyan,
                                fontSize = 14.sp
                            )

                            /*
                            Text(
                                text = "Edad: ${nurse.getAge()}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                text = "Fecha: ${nurse.getRegisterFormatDate()}",
                                style = MaterialTheme.typography.bodyMedium
                            )

                             */
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        } else if (searchList.isNotEmpty()) {
            Text("No results found", style = MaterialTheme.typography.bodyLarge)
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