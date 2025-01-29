package com.example.panacea.data.network

import com.example.panacea.data.models.nurse.Nurse
import com.example.panacea.data.models.nurse.NurseResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json

class NetworkServices() {

    private val client = HttpClient()
    val jsonData = Json { ignoreUnknownKeys = true }

    suspend fun getNurses(): List<Nurse> {

        val allNurses = mutableListOf<Nurse>()

        // TODO : Descomentar segun el PC
        //val response = client.get("http://10.118.4.237:8080/nurse")           // MARTINA
        //val response = client.get("http://10.118.3.254:8080/nurse")           // JIAHAO
        val response = client.get("http://10.118.3.210:8080/nurse")    // ENRIC

        val nurseResponse: NurseResponse = jsonData.decodeFromString(response.bodyAsText())

        println(nurseResponse)

        val validNurses = nurseResponse.data.filterNot { nurse ->
            nurse.name.isNullOrBlank() ||
                    nurse.surname.isNullOrBlank() ||
                    nurse.birthDate == null ||
                    nurse.email.isNullOrBlank()
        }

        allNurses.addAll(validNurses)

        return allNurses
    }

}