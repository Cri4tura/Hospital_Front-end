package com.example.hospital_front_end.ui.screens.directory

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import com.example.hospital_front_end.nurseRepository.NurseRepository
import com.example.hospital_front_end.ui.components.DrawerAppBar
import com.example.hospital_front_end.ui.components.NurseItem
import com.example.hospital_front_end.navigation.NavigationController

@Composable
fun NurseList(nav: NavigationController) {
    val nurseList by nav.nurseList.collectAsState()

    DrawerAppBar(

        nav = nav,
        index = 3,
        pageTitle = { Text("Search") },
        floatingActionButton = {
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

                Spacer(modifier = Modifier.height(100.dp))

                if (nurseList.isNotEmpty()) {
                    LazyColumn(contentPadding = PaddingValues(bottom = 8.dp)) {

                        items(nurseList.sortedBy { it.name }) { nurse ->
                            NurseItem(nurse, nav)
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                } else {
                    Text("No results found", style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
    )
}

@Preview()
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MyNurseListPreview() {
    val nurseList = NurseRepository().getNurseList()
    NurseList(
        nav = NavigationController(NurseRepository())
    )
}