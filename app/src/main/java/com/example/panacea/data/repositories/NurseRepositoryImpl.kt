package com.example.panacea.data.repositories

import androidx.compose.runtime.mutableStateOf
import com.example.panacea.domain.models.nurse.Nurse
import com.example.panacea.data.network.NetworkServicesImpl
import com.example.panacea.domain.repositories.NurseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.text.SimpleDateFormat

class NurseRepositoryImpl(
    private val conn: NetworkServicesImpl
) : NurseRepository {
    private var isDeleted = false
    private var nurseList = mutableListOf<Nurse>()
    private var currentNurse: Nurse = Nurse(
        id = 1,
        name = "Laura",
        surname = "García",
        email = "laura.garcia@example.com",
        password = "securePassword123",
        birthDate = SimpleDateFormat("dd/MM/yyyy").parse("15/03/1990"),
        registerDate = SimpleDateFormat("dd/MM/yyyy").parse("01/02/2024")
    )


    override val remoteNurses: Flow<List<Nurse>> = flow {
        val nurses = conn.getNurses()
        emit(nurses)
    }

    override fun getNurseById(nurseID: Int): Flow<Nurse> = flow {
        val nurse = conn.getNurseById(nurseID)
        emit(nurse)
    }

    override fun validateLogin(email: String, password: String): Flow<Nurse?> = flow {
        currentNurse = checkNotNull(conn.validateLogin(email, password))
        emit(currentNurse)
    }

    override fun deleteAccount(userId: Int): Flow<Boolean> = flow {
        println("Deleting user to repository --> userId = $userId")
//        isDeleted = true   /* probar funcion sin borrar BBDD */
        isDeleted= conn.deleteAccount(userId)
        emit(isDeleted)
    }

    override fun getCurrentNurse(): Nurse {
        return currentNurse
    }

    override fun addNurse(nurse: Nurse) {
        println("Adding nurse to repository ${nurse.name} ${nurse.surname}")
        nurseList.add(nurse)
    }

}