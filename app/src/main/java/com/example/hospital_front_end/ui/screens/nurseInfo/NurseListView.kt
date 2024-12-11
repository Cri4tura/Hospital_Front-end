package com.example.hospital_front_end.ui.screens.nurseInfo

import android.content.res.Configuration
import android.os.Build
import androidx.annotation.RequiresApi
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
import com.example.hospital_front_end.model.nurse.Nurse
import com.example.hospital_front_end.nurseRepository.NurseRepository
import com.example.hospital_front_end.ui.navigation.NurseItem


@Composable
fun NurseList(nurseList: List<Nurse>, onBack: () -> Unit, navigateToProfile: (Nurse) -> Unit) {

    Column(
        modifier = Modifier
            .padding(16.dp)
            .padding(top = 20.dp)
    ) {

        IconButton(onClick = onBack, modifier = Modifier.align(Alignment.End)) {
            Icon(imageVector = Icons.Filled.Close, contentDescription = "Back")
        }

        Row(verticalAlignment = Alignment.CenterVertically) {

            Icon(
                painter = painterResource(id = R.drawable.list),
                contentDescription = "List Icon",
                modifier = Modifier.size(24.dp)
            )
            Text(
                "Nurses",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        if (nurseList.isNotEmpty()) {
            LazyColumn(contentPadding = PaddingValues(bottom = 8.dp)) {
                items(nurseList) { nurse ->
                    NurseItem(nurse, navigateToProfile)
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
        onBack = { /* Simulated Back Action */ },
        navigateToProfile = { /* Simulated Profile Action */ }
    )
}