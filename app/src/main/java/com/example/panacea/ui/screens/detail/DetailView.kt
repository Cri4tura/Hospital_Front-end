package com.example.panacea.ui.screens.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.panacea.ui.components.NurseExtendedItem
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun DetailView(
    nurseId: Int,
    nav: NavHostController,
    vm: DetailViewModel = koinViewModel { parametersOf(nurseId) }
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .padding(top = 20.dp)
    ) {

        IconButton(
            onClick = { nav.popBackStack() },
            modifier = Modifier.align(Alignment.End)
        ) {
            Icon(imageVector = Icons.Filled.Close, contentDescription = "Back")
        }

        Spacer(modifier = Modifier.height(32.dp))

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            vm.state.nurse?.let {
                NurseExtendedItem(
                    nurse = it
                )
            }

            if (vm.state.isLoading) {
                CircularProgressIndicator()
            }
        }
    }
}