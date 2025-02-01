package com.example.panacea.ui.screens.directory

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.panacea.R
import com.example.panacea.data.models.nurse.Nurse
import com.example.panacea.ui.navigation.HOME
import com.example.panacea.ui.components.DrawerAppBar
import com.example.panacea.ui.components.NurseItem
import com.example.panacea.data.utils.Constants.MENU
import com.example.panacea.ui.components.searchInput
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel
import kotlin.text.contains

@Composable
fun DirectoryView(
    nav: NavHostController,
    vm: DirectoryViewModel
) {
    val context = LocalContext.current
    var searchQuery by remember { mutableStateOf("") }
    var filteredNurses by remember { mutableStateOf(listOf<Nurse>()) }

    LaunchedEffect(searchQuery, vm.data.nurseList) {
        filteredNurses = vm.data.nurseList
            .filter {
                it.name.contains(searchQuery, ignoreCase = true) ||
                        it.surname.contains(searchQuery, ignoreCase = true) ||
                        it.email.contains(searchQuery, ignoreCase = true) ||
                        it.age.toString().contains(searchQuery)
            }
            .sortedBy { it.name }
    }

    DrawerAppBar(
        nav = nav,
        index = MENU.OPTION_1,
        pageTitle = {
            Image(
                painter = painterResource(id = R.drawable.panacea),
                contentDescription = "Glide image ",
                modifier = Modifier
                    .height(40.dp)
                    .clickable {
                        nav.navigate(HOME)
                    }
            )
        },
        userName = "${vm.data.currentUser?.name} ${vm.data.currentUser?.surname}",
        screenContent = {

            Box(
                contentAlignment = Alignment.TopCenter,
            ) {
                searchQuery = searchInput(searchQuery)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxSize(),
            ) {
                if (vm.state.isLoading) {
                    Box(
                        contentAlignment = Alignment.Center,
                    ) {
                        CircularProgressIndicator()
                    }
                } else {
                    Box (
                        contentAlignment = Alignment.TopStart
                    ){
                        if (filteredNurses.isNotEmpty()) {
                            LazyColumn(contentPadding = PaddingValues(bottom = 8.dp), modifier = Modifier.padding(top= 64.dp)) {
                                items(filteredNurses) { nurse ->
                                    NurseItem(context, nurse, nav)
                                    Spacer(modifier = Modifier.height(8.dp))
                                }
                            }
                        } else if (searchQuery.isNotEmpty()) {
                            Text(

                                text = "No results found",
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(bottom = 8.dp, top = 64.dp)
                            )
                        }
                    }

                }
            }

        }
    )
}
