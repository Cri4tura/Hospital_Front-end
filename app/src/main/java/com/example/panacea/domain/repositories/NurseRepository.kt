package com.example.panacea.domain.repositories

import com.example.panacea.domain.models.nurse.Nurse
import kotlinx.coroutines.flow.Flow

interface NurseRepository {

    var nurseList: List<Nurse>
    var currentNurse: Nurse?

    fun login(email: String, password: String): Flow<Nurse?>
    fun getCurrentUser(): Nurse?
    fun getNurseById(nurseID: Int): Flow<Nurse>
    fun getCachedNurseList(): List<Nurse>

    // CRUD
    suspend fun fetchNurseList()
    fun deleteNurse(userId: Int): Flow<Boolean>
    fun updateNurse(updateData: Nurse): Flow<Nurse>
    suspend fun signinNurse(nurse: Nurse): Flow<Nurse>

}