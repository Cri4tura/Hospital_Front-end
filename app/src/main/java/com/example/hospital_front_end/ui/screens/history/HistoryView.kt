package com.example.hospital_front_end.ui.screens.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.hospital_front_end.navigation.NavigationController
import com.example.hospital_front_end.ui.components.DrawerAppBar
import com.example.hospital_front_end.utils.Constants

@Composable
fun HistoryView(nav: NavigationController) {

    DrawerAppBar(
        nav = nav,
        index = Constants.MENU.OPTION_0.ordinal,
        pageTitle = { Text("History") },
        screenContent = {
            Column (
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("HistoryView")
            }
        }
    )
}