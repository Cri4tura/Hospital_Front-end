package com.example.panacea.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.panacea.R
import com.example.panacea.data.models.nurse.Nurse
import com.example.panacea.ui.components.DrawerAppBar
import com.example.panacea.data.utils.Constants.MENU
import kotlinx.coroutines.launch

@Composable
fun HomeView(
    nav: NavHostController,
    vm: HomeViewModel
) {

    DrawerAppBar(
        nav = nav,
        index = MENU.OPTION_2,
        userName = "${vm.data.currentUser?.name} ${vm.data.currentUser?.surname}",
        pageTitle = {
            Image(
                painter = painterResource(id = R.drawable.panacea),
                contentDescription = "Glide image ",
                modifier = Modifier.height(40.dp)
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { },
                containerColor = MaterialTheme.colorScheme.primary,
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        },
        screenContent = {

            Box (
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ){
                if(vm.state.isLoading){
                    CircularProgressIndicator()
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        items(vm.data.nurseList) { nurse ->

                            var isFilled by remember { mutableStateOf(false) }

                            Card(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxWidth(),
                                elevation = CardDefaults.cardElevation(4.dp),
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp)
                                ) {
                                    Spacer(modifier = Modifier.height(16.dp))

                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {

                                        Box(
                                            modifier = Modifier
                                                .size(40.dp)
                                                .clip(CircleShape)
                                                .background(MaterialTheme.colorScheme.primary),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = nurse.name[0].uppercase(),
                                                color = Color.White,
                                                style = MaterialTheme.typography.titleMedium,
                                            )
                                        }
                                        IconButton(
                                            onClick = {
                                                isFilled = !isFilled
                                            },
                                            modifier = Modifier.size(45.dp).padding(4.dp)
                                        ) {
                                            Icon(
                                                modifier = Modifier.fillMaxSize(),
                                                imageVector = if (isFilled) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                                contentDescription = "Star Icon",
                                                tint = MaterialTheme.colorScheme.primary
                                            )
                                        }

                                    }

                                    Spacer(modifier = Modifier.height(16.dp))

                                    Text(
                                        text = "${nurse.name} ${nurse.surname}",
                                        style = MaterialTheme.typography.titleLarge
                                    )
                                    Text(
                                        text = nurse.email,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )

                                    Spacer(modifier = Modifier.height(16.dp))

                                    Text(
                                        text = "Material is a design system – backed by open source code – that helps teams build high-quality digital experiences.",
                                        style = MaterialTheme.typography.bodySmall
                                    )

                                    Spacer(modifier = Modifier.height(16.dp))
                                }
                            }
                        }
                    }
                }
            }

        }
    )
}

