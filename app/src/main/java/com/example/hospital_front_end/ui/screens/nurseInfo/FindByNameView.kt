package com.example.hospital_front_end.ui.screens.nurseInfo

import androidx.compose.foundation.Image
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
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hospital_front_end.R
import com.example.hospital_front_end.model.nurse.Nurse
import com.example.hospital_front_end.nurseRepository.NurseRepository
import kotlinx.coroutines.delay
import kotlin.text.contains

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FindByNameView(nurseList: List<Nurse>, onBack: () -> Unit) {
    var searchQuery by remember { mutableStateOf("") }
    var filteredNurses by remember { mutableStateOf(listOf<Nurse>()) }


    LaunchedEffect(searchQuery) {
        delay(300)
        filteredNurses = nurseList.filter { nurse ->
            nurse.getName().contains(searchQuery, ignoreCase = true) || nurse.getLastName()
                .contains(searchQuery, ignoreCase = true)
        }
    }

    Column(modifier = Modifier.padding(16.dp).padding(top = 20.dp)) {
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
                "Find nurse by name",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { query -> searchQuery = query },
            label = { Text("Insert nurse name", style = MaterialTheme.typography.bodyLarge) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(15.dp),
            colors = TextFieldDefaults.textFieldColors(
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
                    NurseItem(nurse)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        } else if (searchQuery.isNotEmpty()) {
            Text("No results found", style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@Composable
fun NurseItem(nurse: Nurse) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(id = R.drawable.user), // Consider using Material Icons
            contentDescription = "Nurse Icon", modifier = Modifier.size(80.dp)
        )
        Column(modifier = Modifier.padding(start = 16.dp)) {
            Text(
                text = "${nurse.getName()} ${nurse.getLastName()}", // Assuming you added fullName to Nurse
                style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = nurse.getEmail(),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Cyan
            )
            // ... other nurse details
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FindByNameViewPreview() {
    val nurseList = NurseRepository().getNurseList()
    FindByNameView(nurseList = nurseList, onBack = { /* Simulated Back Action */ })
}