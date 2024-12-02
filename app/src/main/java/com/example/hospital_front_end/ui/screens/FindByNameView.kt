package com.example.hospital_front_end.ui.screens

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
import androidx.compose.ui.unit.dp
import com.example.hospital_front_end.R

@Composable
fun FindByNameView(nurseList: List<String>, onBack: () -> Unit) {
    var searchList by remember { mutableStateOf("") }
    var searchResults by remember { mutableStateOf(listOf<String>()) }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .padding(top = 40.dp)
    ) {
        Text(
            "Find nurse by name",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 8.dp).align(Alignment.CenterHorizontally)
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
                searchResults = nurseList.filter { it.contains(query, ignoreCase = true) }
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
                    text = nurse,
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


