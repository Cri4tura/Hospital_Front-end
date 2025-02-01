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
        /*
        nurseList = arrayListOf(

            Nurse(
                id = 1,
                name = "Juan",
                surname = "Pérez",
                email = "juan.perez@example.com",
                registerDate = dateFormat.parse("2024-01-01")!!,
                birthDate = dateFormat.parse("1994-12-15")!!
            ),

            Nurse(
                id = 2,
                name = "María",
                surname = "Gómez",
                email = "maria.gomez@example.com",
                registerDate = dateFormat.parse("2023-05-10")!!,
                birthDate = dateFormat.parse("1996-03-22")!!
            ),
            Nurse(
                id = 3,
                name = "Luis",
                surname = "Fernández",
                email = "luis.fernandez@example.com",
                registerDate = dateFormat.parse("2022-09-05")!!,
                birthDate = dateFormat.parse("1988-06-30")!!
            ),
            Nurse(
                id = 4,
                name = "Ana",
                surname = "López",
                email = "ana.lopez@example.com",
                registerDate = dateFormat.parse("2021-11-12")!!,
                birthDate = dateFormat.parse("1984-02-17")!!
            ),
            Nurse(
                id = 5,
                name = "Carlos",
                surname = "Díaz",
                email = "carlos.diaz@example.com",
                registerDate = dateFormat.parse("2024-02-20")!!,
                birthDate = dateFormat.parse("1995-08-10")!!
            ),
            Nurse(
                id = 6,
                name = "Sofía",
                surname = "Ramírez",
                email = "sofia.ramirez@example.com",
                registerDate = dateFormat.parse("2020-04-25")!!,
                birthDate = dateFormat.parse("1992-07-18")!!
            ),
            Nurse(
                id = 7,
                name = "Diego",
                surname = "Herrera",
                email = "diego.herrera@example.com",
                registerDate = dateFormat.parse("2023-07-15")!!,
                birthDate = dateFormat.parse("1997-09-29")!!
            ),
            Nurse(
                id = 8,
                name = "Paula",
                surname = "Ortiz",
                email = "paula.ortiz@example.com",
                registerDate = dateFormat.parse("2021-03-03")!!,
                birthDate = dateFormat.parse("1990-01-25")!!
            ),
            Nurse(
                id = 9,
                name = "Andrés",
                surname = "Castro",
                email = "andres.castro@example.com",
                registerDate = dateFormat.parse("2022-08-14")!!,
                birthDate = dateFormat.parse("1993-05-20")!!
            ),
            Nurse(
                id = 10,
                name = "Elena",
                surname = "Vargas",
                email = "elena.vargas@example.com",
                registerDate = dateFormat.parse("2024-06-30")!!,
                birthDate = dateFormat.parse("1999-11-07")!!
            ),
            Nurse(
                id = 11,
                name = "Laura",
                surname = "Jiménez",
                email = "laura.jimenez@example.com",
                registerDate = dateFormat.parse("2023-09-12")!!,
                birthDate = dateFormat.parse("1991-04-05")!!
            ),
            Nurse(
                id = 12,
                name = "Javier",
                surname = "Sánchez",
                email = "javier.sanchez@example.com",
                registerDate = dateFormat.parse("2022-02-28")!!,
                birthDate = dateFormat.parse("1998-10-18")!!
            ),
            Nurse(
                id = 13,
                name = "Isabel",
                surname = "Ruiz",
                email = "isabel.ruiz@example.com",
                registerDate = dateFormat.parse("2024-03-15")!!,
                birthDate = dateFormat.parse("1987-07-02")!!
            ),
            Nurse(
                id = 14,
                name = "Miguel",
                surname = "Torres",
                email = "miguel.torres@example.com",
                registerDate = dateFormat.parse("2021-06-08")!!,
                birthDate = dateFormat.parse("1994-01-11")!!
            ),
            Nurse(
                id = 15,
                name = "Patricia",
                surname = "Flores",
                email = "patricia.flores@example.com",
                registerDate = dateFormat.parse("2020-12-22")!!,
                birthDate = dateFormat.parse("1989-09-30")!!
            ),
            Nurse(
                id = 16,
                name = "Daniel",
                surname = "Moreno",
                email = "daniel.moreno@example.com",
                registerDate = dateFormat.parse("2023-10-29")!!,
                birthDate = dateFormat.parse("1992-06-14")!!
            ),
            Nurse(
                id = 17,
                name = "Lucía",
                surname = "Suárez",
                email = "lucia.suarez@example.com",
                registerDate = dateFormat.parse("2022-05-17")!!,
                birthDate = dateFormat.parse("1995-03-07")!!
            ),
            Nurse(
                id = 18,
                name = "Pablo",
                surname = "Alonso",
                email = "pablo.alonso@example.com",
                registerDate = dateFormat.parse("2024-04-01")!!,
                birthDate = dateFormat.parse("1990-12-26")!!
            ),
            Nurse(
                id = 19,
                name = "Marta",
                surname = "Gutiérrez",
                email = "marta.gutierrez@example.com",
                registerDate = dateFormat.parse("2021-08-23")!!,
                birthDate = dateFormat.parse("1997-08-19")!!
            ),
            Nurse(
                id = 20,
                name = "Sergio",
                surname = "Navarro",
                email = "sergio.navarro@example.com",
                registerDate = dateFormat.parse("2020-01-15")!!,
                birthDate = dateFormat.parse("1986-05-03")!!
            )
        )

         */
    }

    fun getNurseByID(nurseID: Int): Nurse? {
        return nurseList.find { it.id == nurseID }
    }

    fun getNurseList(): List<Nurse> {
        return nurseList
    }

    fun addNurse(nurse: Nurse) {
        println("Adding nurse to repository ${nurse.name} ${nurse.surname}")
        nurseList.add(nurse)
    }

    fun signinNurse(nurse: Nurse): Flow<Boolean> = flow {
        val success = conn.register(nurse)

        emit(success)
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