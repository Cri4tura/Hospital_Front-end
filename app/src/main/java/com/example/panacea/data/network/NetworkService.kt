package com.example.panacea.data.network

import com.example.panacea.data.models.nurse.Nurse
import com.example.panacea.data.models.nurse.NurseResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.client.statement.request
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class NetworkServices() {

    private val client = HttpClient()
    private val jsonData = Json { ignoreUnknownKeys = true }

    suspend fun getNurses(): List<Nurse> {
        val allNurses = mutableListOf<Nurse>()

        val response = client.get("http://192.168.1.73:8080/nurse")  // Cambiar según el servidor

        val nurseResponse: NurseResponse = jsonData.decodeFromString(response.bodyAsText())

        val validNurses = nurseResponse.data.filterNot { nurse ->
            nurse.name.isNullOrBlank() ||
                    nurse.surname.isNullOrBlank() ||
                    nurse.birthDate == null ||
                    nurse.email.isNullOrBlank()
        }

        allNurses.addAll(validNurses)

        return allNurses
    }

    suspend fun register(nurse: Nurse): Boolean {
        val response = client.post("http://192.168.1.73:8080/nurse/signin") {
            contentType(ContentType.Application.Json)
            setBody(Json.encodeToString(nurse))
        }

        println("--> ${response.request.method.value}  ${response.request.url} ")
        println(response.bodyAsText())
        println("<-- END ${response.request.method.value}  ${response.request.url}")
        println("<-- RESPONSE CODE ${response.status}")

        return response.status.value == 201 // Estatus del CREATE
    }


}