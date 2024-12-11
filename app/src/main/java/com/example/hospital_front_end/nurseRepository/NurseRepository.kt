package com.example.hospital_front_end.nurseRepository

import android.annotation.SuppressLint
import com.example.hospital_front_end.models.nurse.Nurse
import java.text.SimpleDateFormat

class NurseRepository {
    private var nurseList = mutableListOf<Nurse>()

    @SuppressLint("SimpleDateFormat")
    fun getNurseList(): ArrayList<Nurse> {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
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
             )
        )
        return nurseList as ArrayList<Nurse>
    }

}
