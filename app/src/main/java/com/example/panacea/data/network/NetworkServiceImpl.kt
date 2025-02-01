package com.example.panacea.data.network

import androidx.compose.runtime.mutableStateOf
import com.example.panacea.domain.models.nurse.Nurse
import com.example.panacea.domain.models.nurse.NurseResponse
import com.example.panacea.domain.repositories.NetworkServices
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.statement.bodyAsText
import io.ktor.client.statement.request
import kotlinx.serialization.json.Json

class NetworkServicesImpl(
    private val client: HttpClient
) : NetworkServices {

    override suspend fun getNurses(): List<Nurse> {
        val allNurses = mutableListOf<Nurse>()
        val response = client.get("/nurse")
        val nurseResponse: NurseResponse = Json.decodeFromString(response.bodyAsText())
        nurseResponse.data.forEach { nurse ->
            allNurses.add(nurse)
        }
        return allNurses
    }

    override suspend fun getNurseById(nurseId: Int): Nurse {
        val response = client.get("/nurse/id/$nurseId")
        val nurseResponse: Nurse = Json.decodeFromString<Nurse>(response.bodyAsText())
        return nurseResponse
    }

    override suspend fun validateLogin(email: String, password: String): Nurse? {

        val response = client.post("/nurse/login") {
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

    override suspend fun deleteAccount(userId: Int): Boolean {
        val response = client.delete("/nurse/$userId")
        val body = response.bodyAsText()

        println("--> ${response.request.method.value}  ${response.request.url} ")
        println(body)
        println("<-- END ${response.request.method.value}  ${response.request.url}")
        println("<-- RESPONSE CODE ${response.status}")

        if (response.status.value == 200) {
            val isDeleted = Json.decodeFromString<Boolean>(response.bodyAsText())
            return isDeleted
        } else {
            println("DELETE FAILED")
            return false
        }
    }
}
