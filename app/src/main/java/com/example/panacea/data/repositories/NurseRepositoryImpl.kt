package com.example.panacea.data.repositories

import com.example.panacea.data.network.NetworkServicesImpl
import com.example.panacea.domain.models.nurse.Nurse
import com.example.panacea.domain.repositories.NurseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.text.SimpleDateFormat
import java.util.Date

class NurseRepositoryImpl(
    private val conn: NetworkServicesImpl
) : NurseRepository {

    private var isDeleted = false

    override var nurseList : List<Nurse> = emptyList()
    override var currentNurse: Nurse? = null


    override fun login(email: String, password: String): Flow<Nurse?> = flow {
        currentNurse = checkNotNull(conn.login(email, password))
        emit(currentNurse)
    }

    override fun getCurrentUser(): Nurse? {
        return currentNurse
    }

    override fun getNurseById(nurseID: Int): Flow<Nurse> = flow {
        emit(conn.getNurseById(nurseID))
    }

    override fun getCachedNurseList(): List<Nurse> {
        return nurseList
    }

    // CRUD
    override suspend fun fetchNurseList() {
        nurseList = conn.getNurses()
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
        emit(currentNurse!!)
    }

    override suspend fun signinNurse(nurse: Nurse): Flow<Nurse> = flow {
        val registeredNurse = conn.register(nurse)  // register devuelve un Nurse? (o null si falla)

        if (registeredNurse.id != -1) {
            currentNurse = registeredNurse
            //nurseList.add(registeredNurse)
            emit(nurse)  // Signin exitoso
        } else {
            val errorNurse = Nurse(
                id = -1, name = "", surname = "", email = "", password = "",
                birthDate = Date(), registerDate = Date()
            )
            currentNurse = errorNurse
            emit(errorNurse) // Signin falló
        }
    }
}