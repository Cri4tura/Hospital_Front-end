package com.example.hospital_front_end.ui.screens.profile

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hospital_front_end.R
import com.example.hospital_front_end.model.nurse.Nurse
import com.example.hospital_front_end.nurseRepository.NurseRepository

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProfileView(nurse: Nurse, onBack: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .padding(top = 20.dp)
    ) {

        IconButton(onClick = onBack, modifier = Modifier.align(Alignment.End)) {
            Icon(imageVector = Icons.Filled.Close, contentDescription = "Back")
        }

        Spacer(modifier = Modifier.height(32.dp))

        Column (
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            NurseExtendedItem(
                nurse = nurse
            )
        }

    }

}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NurseExtendedItem(nurse: Nurse) {

    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(id = R.drawable.user),
            contentDescription = "Nurse Icon", modifier = Modifier.size(120.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = "${nurse.name} ${nurse.surname}",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp
        )
    }

    Spacer(modifier = Modifier.height(20.dp))
    
    Column {
        Row {
            Text(
                text = "Age: ",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Cyan,
                fontSize = 22.sp
            )
            Text(
                text = nurse.age.toString(),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Cyan,
                fontSize = 22.sp
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
        Row {
            Text(
                text = "Birth Date: ",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Cyan,
                fontSize = 22.sp
            )
            Text(
                text = nurse.formatDate(nurse.birthDate),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Cyan,
                fontSize = 22.sp
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            Text(
                text = "Email: ",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Cyan,
                fontSize = 22.sp
            )
            Text(
                text = nurse.email,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Cyan,
                fontSize = 22.sp
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
        Row {
            Text(
                text = "Register Date: ",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Cyan,
                fontSize = 22.sp
            )
            Text(
                text = nurse.formatDate(nurse.registerDate),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Cyan,
                fontSize = 22.sp
            )
        }
    }

}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ProfileViewPreview() {
    ProfileView(
        nurse = NurseRepository().getNurseList()[0],
        onBack = { /* Simulated Back Action */ }
    )

}