package com.example.hospital_front_end.nurseRepository

import com.example.hospital_front_end.model.nurse.Nurse
import java.util.Date

class NurseRepository {
    private var nurseList = mutableListOf<Nurse>()

    fun getNurseList(): ArrayList<Nurse> {
         nurseList = arrayListOf(
            Nurse(1, "Juan", "Pérez", 30, "juan.perez@example.com", Date()),
            Nurse(2, "María", "Gómez", 28, "maria.gomez@example.com", Date()),
            Nurse(3, "Luis", "Fernández", 35, "luis.fernandez@example.com", Date()),
            Nurse(4, "Ana", "López", 40, "ana.lopez@example.com", Date()),
            Nurse(5, "Carlos", "Díaz", 29, "carlos.diaz@example.com", Date()),
            Nurse(6, "Sofía", "Ramírez", 32, "sofia.ramirez@example.com", Date()),
            Nurse(7, "Diego", "Herrera", 27, "diego.herrera@example.com", Date()),
            Nurse(8, "Paula", "Ortiz", 33, "paula.ortiz@example.com", Date()),
            Nurse(9, "Andrés", "Castro", 31, "andres.castro@example.com", Date()),
            Nurse(10, "Elena", "Vargas", 25, "elena.vargas@example.com", Date())
        )
        return nurseList as ArrayList<Nurse>
    }

}
