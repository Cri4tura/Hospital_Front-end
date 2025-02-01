package com.example.panacea.domain.repositories

import com.example.panacea.domain.models.nurse.Nurse
import kotlinx.coroutines.flow.Flow

interface NurseRepository {
    val remoteNurses: Flow<List<Nurse>>

    fun getNurseById(nurseID: Int): Flow<Nurse>
    fun validateLogin(email: String, password: String): Flow<Nurse?>
    fun getCurrentNurse(): Nurse
    fun addNurse(nurse: Nurse)
    fun deleteAccount(userId: Int): Flow<Boolean>
}