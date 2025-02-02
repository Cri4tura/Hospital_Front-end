package com.example.panacea.domain.repositories

import com.example.panacea.domain.models.nurse.Nurse
import kotlinx.coroutines.flow.Flow
import java.util.Date

interface NurseRepository {
    val remoteNurses: Flow<List<Nurse>>

    fun getNurseById(nurseID: Int): Flow<Nurse>
    fun validateLogin(email: String, password: String): Flow<Nurse?>
    fun getCurrentNurse(): Nurse?
    suspend fun getNurseList(): List<Nurse>
    fun addNurse(nurse: Nurse)
    fun deleteNurse(userId: Int): Flow<Boolean>
    fun updateNurse(updateData: Nurse): Flow<Nurse>
}