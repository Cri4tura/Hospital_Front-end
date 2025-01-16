package com.example.hospital_front_end.ui.screens.documents

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
fun DocsView(nav: NavigationController) {

    DrawerAppBar(
        nav = nav,
        index = Constants.MENU.OPTION_3.ordinal,
        pageTitle = { Text("Documents") },
        screenContent = {
            Column (
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("DocsView")
            }
        }
    )
}