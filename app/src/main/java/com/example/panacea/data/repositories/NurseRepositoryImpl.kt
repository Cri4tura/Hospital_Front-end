package com.example.panacea.data.repositories

import androidx.compose.runtime.mutableStateOf
import com.example.panacea.domain.models.nurse.Nurse
import com.example.panacea.data.network.NetworkServicesImpl
import com.example.panacea.domain.repositories.NurseRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date

class NurseRepositoryImpl(
    private val conn: NetworkServicesImpl
) : NurseRepository {
    private var isDeleted = false
    private var nurseList = mutableListOf<Nurse>()
    private var currentNurse: Nurse = Nurse(
        id = 1,
        name = "Guest",
        surname = " ",
        email = "gest@example.com",
        password = "password123",
        birthDate = SimpleDateFormat("dd/MM/yyyy").parse("15/03/1990"),
        registerDate = SimpleDateFormat("dd/MM/yyyy").parse("01/02/2024")
    )

    init {
        CoroutineScope(Dispatchers.IO).launch {
            nurseList = conn.getNurses().toMutableList()
        }
    }

    override val remoteNurses: Flow<List<Nurse>> = flow {
        val nurses = conn.getNurses()
        emit(nurses)
    }

    override fun getNurseById(nurseID: Int): Flow<Nurse> = flow {
        emit(conn.getNurseById(nurseID))
    }

    override fun validateLogin(email: String, password: String): Flow<Nurse?> = flow {
        currentNurse = checkNotNull(conn.validateLogin(email, password))
        emit(currentNurse)
    }

    override fun deleteNurse(userId: Int): Flow<Boolean> = flow {
        println("Deleting user to repository --> userId = $userId")
//        isDeleted = true   /* probar funcion sin borrar BBDD */
        isDeleted = conn.deleteNurse(userId)
        emit(isDeleted)
    }

    override fun updateNurse(updateData: Nurse): Flow<Nurse> = flow {
        println("UPDATE DATA $updateData")
        val newNurse = Nurse(
            id = updateData.id,
            name = updateData.name,
            surname = updateData.surname,
            email = updateData.email,
            password = updateData.password,
            birthDate = updateData.birthDate,
            registerDate = updateData.registerDate
        )
        currentNurse = conn.updateNurse(newNurse)
        println("CURRENT NURSE $currentNurse")
        emit(currentNurse)
    }

    override fun getCurrentNurse(): Nurse {
        return currentNurse
    }

    override fun getNurseList(): List<Nurse> {
        return nurseList
    }

    override fun addNurse(nurse: Nurse) {
        println("Adding nurse to repository ${nurse.name} ${nurse.surname}")
        nurseList.add(nurse)
    }

}