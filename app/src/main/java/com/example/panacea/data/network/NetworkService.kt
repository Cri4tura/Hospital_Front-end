package com.example.panacea.data.network

import com.example.panacea.data.models.nurse.Nurse
import com.example.panacea.data.models.nurse.NurseResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.statement.bodyAsText
import io.ktor.client.statement.request
import kotlinx.serialization.json.Json

class NetworkServices() {

    private val client = HttpClient()

    suspend fun getNurses(): List<Nurse> {
        val allNurses = mutableListOf<Nurse>()
        val response = client.get("http://10.118.3.210:8080/nurse")  // TODO: Cambiar la IP
        val nurseResponse: NurseResponse = Json.decodeFromString(response.bodyAsText())
        nurseResponse.data.forEach { nurse ->
            allNurses.add(nurse)
        }
        return allNurses
    }

    suspend fun getNurseById(nurseId: Int): Nurse {
        val response = client.get("http://10.118.3.210:8080/nurse/id/$nurseId") // TODO: Cambiar la IP
        val nurseResponse: Nurse = Json.decodeFromString<Nurse>(response.bodyAsText())
        return nurseResponse
    }

    suspend fun validateLogin(email: String, password: String): Nurse? {

        val response = client.post("http://10.118.3.210:8080/nurse/login"){
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