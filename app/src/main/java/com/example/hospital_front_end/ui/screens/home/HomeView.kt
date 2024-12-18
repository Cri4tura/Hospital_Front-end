package com.example.hospital_front_end.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.hospital_front_end.R
import com.example.hospital_front_end.ui.components.LogoutButton
import com.example.hospital_front_end.ui.components.LogoutConfirmationDialog
import com.example.hospital_front_end.ui.components.MyAppBarWithDrawer
import com.example.hospital_front_end.ui.components.PrimaryButton
import com.example.hospital_front_end.ui.navigation.NavigationViewModel
import com.google.ai.client.generativeai.type.content

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun HomeView(
    navViewModel: NavigationViewModel
) {

   MyAppBarWithDrawer(
       content = {},
       navViewModel = navViewModel,
       pageTitle = "Home"
   )

}


@Preview(showBackground = true)
@Composable
fun HomeViewPreview() {
    HomeView(
        navViewModel = NavigationViewModel()
        //onConfirmLogout = { /* Simulated Confirm Logout */ },
        //onViewNurseList = { /* Simulated View List */ },
        //onSearchByName = { /* Simulated Find By Name */ }
    )
}
