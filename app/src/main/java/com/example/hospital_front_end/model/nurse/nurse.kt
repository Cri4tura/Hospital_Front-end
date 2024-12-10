package com.example.hospital_front_end.model.nurse

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.Period
import java.time.ZoneId
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
data class Nurse(
    val id: Int,
    val name: String,
    val surname: String,
    val email: String,
    val registerDate: Date,
    val birthDate: Date
) {
    val age: Int
        get() {
            val today = LocalDate.now()
            val birthLocalDate = birthDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate() // Convert to LocalDate
            return Period.between(birthLocalDate, today).years
        }
}






