package com.example.panacea.ui.screens.directory

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.panacea.R
import com.example.panacea.data.models.nurse.Nurse
import com.example.panacea.navigation.HOME
import com.example.panacea.ui.components.DrawerAppBar
import com.example.panacea.ui.components.NurseItem
import com.example.panacea.utils.Constants.MENU
import kotlinx.coroutines.delay
import org.koin.androidx.compose.getViewModel
import kotlin.text.contains

@Composable
fun FindByNameView(nav: NavHostController, vm: DirectoryViewModel = getViewModel()) {
    var searchQuery by remember { mutableStateOf("") }
    var filteredNurses by remember { mutableStateOf(listOf<Nurse>()) }

    val nurseList = vm.nurseList.collectAsState().value

    LaunchedEffect(searchQuery) {
        delay(300)
        filteredNurses = nurseList.filter { nurse ->
            nurse.name.contains(searchQuery, ignoreCase = true) ||
                    nurse.surname.contains(searchQuery, ignoreCase = true) ||
                    nurse.email.contains(searchQuery, ignoreCase = true) ||
                    nurse.age.toString().contains(searchQuery)
        }
    }

    DrawerAppBar(

        nav = nav,
        index = MENU.OPTION_1,
        pageTitle = {
            Image(
                painter = painterResource(id = R.drawable.panacea),
                contentDescription = "Glide image ",
                modifier = Modifier.height(40.dp).clickable {
                    nav.navigate(HOME)
                }
            )
        },
        userName = null,
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
                            NurseItem(nurse, nav)
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