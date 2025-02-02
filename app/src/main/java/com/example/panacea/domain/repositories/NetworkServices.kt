package com.example.panacea.domain.repositories

import com.example.panacea.domain.models.nurse.Nurse
import java.util.Date

interface NetworkServices {
    suspend fun getNurses(): List<Nurse>
    suspend fun getNurseById(nurseId: Int): Nurse
    suspend fun validateLogin(email: String, password: String): Nurse?
    suspend fun deleteNurse(userId: Int): Boolean
    suspend fun updateNurse(updatedData: Nurse): Nurse

}