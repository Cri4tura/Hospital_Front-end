package com.example.hospital_front_end.model.nurse

import java.text.SimpleDateFormat
import java.util.Date

data class Nurse(
    private val id: Int,
    private val name: String,
    private val lastName: String,
    private val age: Int,
    private val email: String,
    private val registerDate: Date
) {
    fun getId(): Int = id
    fun getName(): String = name
    fun getLastName(): String = lastName
    fun getAge(): Int = age
    fun getEmail(): String = email
    fun getRegisterDate(): Date = registerDate
    fun getRegisterFormatDate(): String {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
        return dateFormat.format(registerDate)
    }
}






