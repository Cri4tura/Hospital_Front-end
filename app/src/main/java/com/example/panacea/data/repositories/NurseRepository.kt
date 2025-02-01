package com.example.panacea.data.repositories

import android.annotation.SuppressLint
import com.example.panacea.data.models.nurse.Nurse
import com.example.panacea.data.network.NetworkServices
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.text.SimpleDateFormat
import java.util.Date

class NurseRepository (
    private val conn: NetworkServices
){
    private var nurseList = mutableListOf<Nurse>()
    private  var currentNurse: Nurse = Nurse(
        id = 1,
        name = "Laura",
        surname = "García",
        email = "laura.garcia@example.com",
        password = "securePassword123",
        birthDate = SimpleDateFormat("dd/MM/yyyy").parse("15/03/1990"),
        registerDate = SimpleDateFormat("dd/MM/yyyy").parse("01/02/2024")
    )

    val remoteNurses: Flow<List<Nurse>> = flow {
        val nurses = conn.getNurses()
        emit(nurses)
    }

    fun getNurseById(nurseID: Int): Flow<Nurse> = flow {
        val nurse = conn.getNurseById(nurseID)
        emit(nurse)
    }

    fun validateLogin(email: String, password: String): Flow<Nurse?> = flow {
        currentNurse = checkNotNull(conn.validateLogin(email, password))
        emit(currentNurse)
    }

    fun getCurrentNurse(): Nurse {
        return currentNurse
    }

    fun addNurse(nurse: Nurse) {
        println("Adding nurse to repository ${nurse.name} ${nurse.surname}")
        nurseList.add(nurse)
    }

}