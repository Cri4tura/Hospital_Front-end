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

    fun getNurseById(nurseID: Int): Flow<Nurse> = flow {
        val nurse = conn.getNurseById(nurseID)
        emit(nurse)
    }

    fun addNurse(nurse: Nurse) {
        println("Adding nurse to repository ${nurse.name} ${nurse.surname}")
        nurseList.add(nurse)
    }

}