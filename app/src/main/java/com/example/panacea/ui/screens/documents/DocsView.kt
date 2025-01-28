package com.example.panacea.ui.screens.documents

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.panacea.R
import com.example.panacea.navigation.HOME
import com.example.panacea.ui.components.DrawerAppBar
import com.example.panacea.utils.Constants

@Composable
fun DocsView(nav: NavHostController) {

    DrawerAppBar(
        nav = nav,
        index = Constants.MENU.OPTION_3.ordinal,
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