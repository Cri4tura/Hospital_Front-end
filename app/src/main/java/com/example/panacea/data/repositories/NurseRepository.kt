package com.example.panacea.data.repositories

import android.annotation.SuppressLint
import com.example.panacea.data.models.nurse.Nurse
import com.example.panacea.data.network.NetworkServices
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.text.SimpleDateFormat

class NurseRepository (
    private val conn: NetworkServices
){
    private var nurseList = mutableListOf<Nurse>()

    val remoteNurses: Flow<List<Nurse>> = flow {
        val nurses = conn.getNurses()
        emit(nurses)
    }

    init {
        loadInitialData()
    }

    @SuppressLint("SimpleDateFormat")
    private fun loadInitialData() {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
    }

    fun getNurseByID(nurseID: Int): Nurse? {
        return nurseList.find { it.id == nurseID }
    }

    fun getNurseList(): List<Nurse> {
        return nurseList
    }

    fun signinNurse(nurse: Nurse): Flow<Boolean> = flow {
        val registeredNurse = conn.register(nurse)  // register devuelve un Nurse? (o null si falla)

        if (registeredNurse != null) {
            nurseList.add(registeredNurse)
            emit(true)  // Signin exitoso
        } else {
            emit(false) // Signin falló
        }
    }

    suspend fun getAllNurses(): List<Nurse> {
        return conn.getNurses()
    }

    fun removeNurseByEmail(email: String): Boolean {
        val nurseToRemove = nurseList.find { it.email == email }
        return if (nurseToRemove != null) {
            nurseList.remove(nurseToRemove)
            true
        } else {
            false
        }
    }

    fun updateNurseByEmail(email: String, updatedNurse: Nurse): Boolean {
        val nurseIndex = nurseList.indexOfFirst { it.email == email }
        if (nurseIndex == -1) return false // Enfermera no encontrada

        val existingNurse = nurseList[nurseIndex]

        // Actualizar los campos solo si no son nulos o vacíos
        nurseList[nurseIndex] = existingNurse.copy(
            id = updatedNurse.id.takeIf { it != 0 } ?: existingNurse.id,
            name = updatedNurse.name.takeIf { it.isNotBlank() } ?: existingNurse.name,
            surname = updatedNurse.surname.takeIf { it.isNotBlank() } ?: existingNurse.surname,
            email = updatedNurse.email.takeIf { it.isNotBlank() } ?: existingNurse.email,
            registerDate = updatedNurse.registerDate,
            birthDate = updatedNurse.birthDate
        )
        return true
    }

    fun getNurseByEmail(email: String): Nurse? {
        return nurseList.find { it.email == email }
    }
}