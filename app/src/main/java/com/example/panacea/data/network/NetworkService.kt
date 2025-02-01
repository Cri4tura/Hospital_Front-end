package com.example.panacea.data.network

import com.example.panacea.data.models.nurse.Nurse
import com.example.panacea.data.models.nurse.NurseResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.request
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.client.statement.request
import kotlinx.serialization.json.Json

class NetworkServices(
    private val client: HttpClient
) {

    suspend fun getNurses(): List<Nurse> {
        val allNurses = mutableListOf<Nurse>()
        val response = client.get("/nurse")
        val nurseResponse: NurseResponse = Json.decodeFromString(response.bodyAsText())
        nurseResponse.data.forEach { nurse ->
            allNurses.add(nurse)
        }
        return allNurses
    }

    suspend fun getNurseById(nurseId: Int): Nurse {
        val response = client.get("/nurse/id/$nurseId")
        val nurseResponse: Nurse = Json.decodeFromString<Nurse>(response.bodyAsText())
        return nurseResponse
    }

    suspend fun validateLogin(email: String, password: String): Nurse? {

        val response = client.post("/nurse/login"){
            url {
                parameters.append("email", email)
                parameters.append("password", password)
            }
        }
        val body = response.bodyAsText()

        println("--> ${response.request.method.value}  ${response.request.url} ")
        println(body)
        println("<-- END ${response.request.method.value}  ${response.request.url}")
        println("<-- RESPONSE CODE ${response.status}")

        if (response.status.value == 200) {
            val nurse = Json.decodeFromString<Nurse>(response.bodyAsText())
            return nurse
        } else {
            println("LOGIN FAILED")
            return null
        }
    }
}