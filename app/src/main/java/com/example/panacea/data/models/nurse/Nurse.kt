package com.example.panacea.data.models.nurse

import com.example.panacea.data.DateSerializer
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Period
import java.time.ZoneId
import java.util.Date

@Serializable
data class Nurse(
    val id: Int,
    val name: String,
    val surname: String,
    val email: String,
    val password: String,
    @Serializable(with = DateSerializer::class)
    val birthDate: Date,
    @Serializable(with = DateSerializer::class)
    val registerDate: Date,
) {
    val age: Int
        get() {
            val today = LocalDate.now()
            val birthLocalDate = birthDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
            return Period.between(birthLocalDate, today).years
        }

    fun formatDate(date: Date): String {
        return SimpleDateFormat("dd/MM/yyyy").format(date)
    }
}






