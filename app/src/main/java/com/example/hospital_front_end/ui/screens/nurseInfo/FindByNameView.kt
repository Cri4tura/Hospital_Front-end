package com.example.hospital_front_end.ui.screens.nurseInfo

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hospital_front_end.models.nurse.Nurse
import com.example.hospital_front_end.nurseRepository.NurseRepository
import com.example.hospital_front_end.ui.components.MyAppBarWithDrawer
import com.example.hospital_front_end.ui.components.NurseItem
import com.example.hospital_front_end.navigation.NavigationViewModel
import kotlinx.coroutines.delay
import org.koin.androidx.compose.getViewModel
import org.koin.androidx.compose.koinViewModel
import kotlin.text.contains

@Composable
fun FindByNameView( navViewModel: NavigationViewModel, findByNameViewModel: FindByNameViewModel = getViewModel()) {
    var searchQuery by remember { mutableStateOf("") }
    var filteredNurses by remember { mutableStateOf(listOf<Nurse>()) }

    val nurseList = findByNameViewModel.nurseList.collectAsState().value

    LaunchedEffect(searchQuery) {
        delay(300)
        filteredNurses = nurseList.filter { nurse ->
            nurse.name.contains(searchQuery, ignoreCase = true) ||
                    nurse.surname.contains(searchQuery, ignoreCase = true) ||
                    nurse.email.contains(searchQuery, ignoreCase = true) ||
                    nurse.age.toString().contains(searchQuery)
        }
    }

    MyAppBarWithDrawer(

        navViewModel = navViewModel,
        pageTitle = "Directory",
        actionButton = {
            FloatingActionButton(
                onClick = {

                },
                containerColor = MaterialTheme.colorScheme.primary,
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        },
        screenContent = {
            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {

                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { query -> searchQuery = query },
                    placeholder = {Text("Search")},
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(15.dp),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    leadingIcon = { Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon"
                    ) },
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
                        items(filteredNurses.sortedBy { it.name }) { nurse ->
                            NurseItem(nurse, navViewModel)
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                } else if (searchQuery.isNotEmpty()) {
                    Text("No results found", style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
    )

}

@Preview(showBackground = true)
@Composable
fun FindByNameViewPreview() {
    FindByNameView(
        navViewModel = NavigationViewModel(NurseRepository()),
        findByNameViewModel = FindByNameViewModel(NurseRepository())
    )
}