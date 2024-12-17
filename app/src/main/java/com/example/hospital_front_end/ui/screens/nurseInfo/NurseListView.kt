package com.example.hospital_front_end.ui.screens.nurseInfo

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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.hospital_front_end.R
import com.example.hospital_front_end.models.nurse.Nurse
import com.example.hospital_front_end.nurseRepository.NurseRepository
import com.example.hospital_front_end.ui.components.MyAppBarWithDrawer
import com.example.hospital_front_end.ui.components.NurseItem
import com.example.hospital_front_end.ui.navigation.NavigationViewModel

@Composable
fun NurseList(nurseList: List<Nurse>, navViewModel: NavigationViewModel) {

    MyAppBarWithDrawer(
        navViewModel = navViewModel,
        pageTitle = "Nurse List",
        //imageResource = R.drawable.list
    )
    Column(
        modifier = Modifier
            .padding(16.dp)
    ) {

        Spacer(modifier = Modifier.height(100.dp))

        if (nurseList.isNotEmpty()) {
            LazyColumn(contentPadding = PaddingValues(bottom = 8.dp)) {
                items(nurseList) { nurse ->
                    NurseItem(nurse, navViewModel)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        } else {
            Text("No results found", style = MaterialTheme.typography.bodyLarge)
        }
    }

}

@Preview()
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MyNurseListPreview() {
    val nurseList = NurseRepository().getNurseList()
    NurseList(
        nurseList = nurseList,
        navViewModel = NavigationViewModel()
    )
}