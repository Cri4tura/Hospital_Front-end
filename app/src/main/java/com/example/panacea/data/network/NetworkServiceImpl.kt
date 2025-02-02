package com.example.panacea.data.network


import com.example.panacea.domain.models.nurse.Nurse
import com.example.panacea.domain.models.nurse.NurseResponse
import com.example.panacea.domain.repositories.NetworkServices
import io.ktor.client.HttpClient
import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.client.statement.request
import io.ktor.client.utils.EmptyContent.contentType
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import kotlinx.serialization.json.Json
import java.io.IOException
import java.util.Date

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

        return try {
            println("LOGIN STARTED..................................................")
            val response = client.post("/nurse/login") {
                url {
                    parameters.append("email", email)
                    parameters.append("password", password)
                }
            }

            println("--> ${response.request.method.value}  ${response.request.url}")
            println(response.bodyAsText())
            println("<-- END ${response.request.method.value}  ${response.request.url}")
            println("<-- RESPONSE CODE ${response.status}")

            if (response.status == HttpStatusCode.OK) {
                val responseBody = response.bodyAsText()
                if (responseBody.isNotEmpty()) {
                    Json.decodeFromString<Nurse>(responseBody)
                } else {
                    println("ERROR: Respuesta vacía del servidor.")
                    null
                }

            } else {
                println("LOGIN FAILED")
                null
            }
        } catch (e: ConnectTimeoutException) {
            println("ERROR: Problema de red. Verifica tu conexión a Internet.")
            null
        } catch (e: IOException) {
            println("ERROR inesperado: ${e.localizedMessage}")
            null
        }
    }

    override suspend fun deleteNurse(userId: Int): Boolean {
        val response = client.delete("/nurse/$userId")
        val body = response.bodyAsText()

        println("--> ${response.request.method.value}  ${response.request.url} ")
        println(body)
        println("<-- END ${response.request.method.value}  ${response.request.url}")
        println("<-- RESPONSE CODE ${response.status}")

        if (response.status == HttpStatusCode.OK) {
            val isDeleted = Json.decodeFromString<Boolean>(response.bodyAsText())
            return isDeleted
        } else {
            println("DELETE FAILED")
            return false
        }
    }

    override suspend fun updateNurse(updatedData: Nurse): Nurse {
        val response = client.put("/nurse/${updatedData.id}") {
            contentType(ContentType.Application.Json)
            setBody(updatedData)
        }
        println(updatedData.toString())
        val body = response.bodyAsText()
        println("--> ${response.request.method.value}  ${response.request.url} ")
        println(body)
        println("<-- END ${response.request.method.value}  ${response.request.url}")
        println("<-- RESPONSE CODE ${response.status}")

        if (response.status == HttpStatusCode.OK) {
            println("Actualización exitosa: ${response.bodyAsText()}")
            return updatedData
        } else {
            println("Error al actualizar: ${response.status}")
            return updatedData
        }

    }
}
