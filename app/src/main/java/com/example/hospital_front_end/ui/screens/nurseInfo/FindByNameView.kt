package com.example.hospital_front_end.ui.screens.nurseInfo

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hospital_front_end.models.nurse.Nurse
import com.example.hospital_front_end.nurseRepository.NurseRepository
import com.example.hospital_front_end.ui.components.NurseItem
import kotlinx.coroutines.delay
import kotlin.text.contains

@Composable
fun FindByNameView(nurseList: List<Nurse>, onBack: () -> Unit, navigateToProfile: (Nurse) -> Unit) {
    var searchQuery by remember { mutableStateOf("") }
    var filteredNurses by remember { mutableStateOf(listOf<Nurse>()) }


    LaunchedEffect(searchQuery) {
        delay(300)
        filteredNurses = nurseList.filter { nurse ->
            nurse.name.contains(searchQuery, ignoreCase = true) ||
            nurse.surname.contains(searchQuery, ignoreCase = true) ||
            nurse.email.contains(searchQuery, ignoreCase = true) ||
            nurse.age.toString().contains(searchQuery)
        }
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .padding(top = 20.dp)
    ) {
        IconButton(onClick = onBack, modifier = Modifier.align(Alignment.End)) {
            Icon(imageVector = Icons.Filled.Close, contentDescription = "Back")
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "Search",
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                "Find nurse",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { query -> searchQuery = query },
            label = { Text("Insert name", style = MaterialTheme.typography.bodyLarge) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(15.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (filteredNurses.isNotEmpty()) {
            Text(
                "Results:",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            LazyColumn(contentPadding = PaddingValues(bottom = 8.dp)) {
                items(filteredNurses) { nurse ->
                    NurseItem(nurse, navigateToProfile)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        } else if (searchQuery.isNotEmpty()) {
            Text("No results found", style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FindByNameViewPreview() {
    val nurseList = NurseRepository().getNurseList()
    FindByNameView(
        nurseList = nurseList,
        onBack = { /* Simulated Back Action */ },
        navigateToProfile = { /* Simulated Profile Action */ })
}