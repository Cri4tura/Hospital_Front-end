package com.example.hospital_front_end.ui.screens.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.example.hospital_front_end.ui.components.MyAppBarWithDrawer
import com.example.hospital_front_end.ui.navigation.NavigationViewModel

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun HomeView(
    navViewModel: NavigationViewModel
) {
   var presses by remember { mutableIntStateOf(0) }

   MyAppBarWithDrawer(
       navViewModel = navViewModel,
       pageTitle = "Home",
       content = { }
   )

}


@Preview(showBackground = true)
@Composable
fun HomeViewPreview() {
    HomeView(
        navViewModel = NavigationViewModel()
    )
}
