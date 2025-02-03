package com.example.panacea.data.network

import android.util.Log
import com.example.panacea.domain.models.nurse.Nurse
import com.example.panacea.domain.models.nurse.NurseResponse
import com.example.panacea.domain.repositories.NetworkServices
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.plugin
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.client.statement.request
import io.ktor.client.utils.EmptyContent.contentType
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import java.io.IOException
import java.util.Date

class NetworkServicesImpl(
    private val client: HttpClient,
    private val json: Json
) : NetworkServices {

    override suspend fun getNurses(): List<Nurse> {
        val allNurses = mutableListOf<Nurse>()
        try {
            val response: HttpResponse = client.get("/nurse")

            if (response.status == HttpStatusCode.OK) {
                val responseBody: String = response.bodyAsText()
                val jsonResponse: JsonObject = json.decodeFromString(responseBody)
                val prettyJson = json.encodeToString(jsonResponse)
                Log.d("NETWORK", prettyJson)

                val nurseResponse: NurseResponse = json.decodeFromString(responseBody)
                nurseResponse.data.forEach { nurse ->
                    allNurses.add(nurse)
                }
            } else {
                if (response.status == HttpStatusCode.NoContent) {
                    Log.e("NETWORK", "HttpStatus NO CONTENT: ${response.status.value}")
                    throw Exception("HttpStatus NO CONTENT: ${response.status.value}")
                }
                Log.e("NETWORK", "STATUS CODE: ${response.status.value}")
                throw Exception("STATUS CODE: ${response.status.value}")
            }
        } catch (e: Exception) {
            Log.e("NETWORK", "Error al obtener enfermeras: ${e.localizedMessage}")
            throw e
        }
        Log.d("NETWORK", "getNurses() ha terminado") // Log para indicar que la función terminó
        return allNurses
    }

    override suspend fun getNurseById(nurseId: Int): Nurse {
        try {
            val response: HttpResponse = client.get("/nurse/id/$nurseId")

            val responseBody: String = response.bodyAsText()
            if (responseBody.isNotEmpty()) {
                try {
                    val jsonResponse: JsonObject = json.decodeFromString(responseBody)
                    val prettyJson = json.encodeToString(jsonResponse)
                    Log.d("NETWORK", prettyJson)
                } catch (e: Exception) {
                    Log.e("NETWORK", "Error parsing JSON response: ${e.localizedMessage}")
                }
            } else {
                Log.e("NETWORK", "Empty response body")
            }
            val nurseResponse: Nurse = json.decodeFromString(responseBody)
            if (response.status == HttpStatusCode.OK) {
                return nurseResponse
            } else {
                Log.e("NETWORK", "Error en la respuesta del servidor: ${response.status.value}")
                throw Exception("Error en la respuesta del servidor: ${response.status.value}")
            }

        } catch (e: ConnectTimeoutException) {
            Log.e("NETWORK", "CUSTOM ${e.localizedMessage}")
            throw e

        } catch (e: Exception) {
            Log.e("NETWORK", "Error al obtener la enfermera con ID $nurseId: ${e.localizedMessage}")
            throw e
        }
    }

    override suspend fun login(email: String, password: String): Nurse? {
        return try {
            val response: HttpResponse = client.post("/nurse/login") {
                url {
                    parameters.append("email", email)
                    parameters.append("password", password)
                }
            }

            val responseBody: String = response.bodyAsText()
            if (responseBody.isNotEmpty()) {
                try {
                    val jsonResponse: JsonObject = json.decodeFromString(responseBody)
                    val prettyJson = json.encodeToString(jsonResponse)
                    Log.d("NETWORK", prettyJson)
                } catch (e: Exception) {
                    Log.e("NETWORK", "Error parsing JSON response: ${e.localizedMessage}")
                }
            } else {
                Log.e("NETWORK", "Empty response body")
            }

            if (response.status == HttpStatusCode.OK) {
                if (responseBody.isNotEmpty()) {
                    Json.decodeFromString<Nurse>(responseBody)
                } else {
                    println("ERROR: Respuesta vacía del servidor.")
                    null
                }
            } else {
                if (response.status == HttpStatusCode.NotFound) {
                    println("Usuario no encontrado en la BBDD")
                }
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

        val responseBody: String = response.bodyAsText()
        if (responseBody.isNotEmpty()) {
            try {
                val jsonResponse: JsonObject = json.decodeFromString(responseBody)
                val prettyJson = json.encodeToString(jsonResponse)
                Log.d("NETWORK", prettyJson)
            } catch (e: Exception) {
                Log.e("NETWORK", "Error parsing JSON response: ${e.localizedMessage}")
            }
        } else {
            Log.e("NETWORK", "Empty response body")
        }

        if (response.status == HttpStatusCode.OK) {
            val isDeleted = Json.decodeFromString<Boolean>(response.bodyAsText())
            return isDeleted
        } else {
            println("DELETE FAILED")
            return false
        }
    }

    override suspend fun updateNurse(updatedData: Nurse): Nurse {
        try {
            val response: HttpResponse = client.put("/nurse/${updatedData.id}") {
                contentType(ContentType.Application.Json)
                setBody(updatedData)
            }

            val responseBody: String = response.bodyAsText()
            if (responseBody.isNotEmpty()) {
                try {
                    val jsonResponse: JsonObject = json.decodeFromString(responseBody)
                    val prettyJson = json.encodeToString(jsonResponse)
                    Log.e("NETWORK", prettyJson)
                } catch (e: Exception) {
                    Log.e("NETWORK", "Error parsing JSON response: ${e.localizedMessage}")
                }
            } else {
                Log.e("NETWORK", "Empty response body")
            }

            if (response.status == HttpStatusCode.OK) {
                Log.d("NETWORK", "Nurse updated successfully: ${json.encodeToString(updatedData)}")
                return updatedData
            } else {
                Log.e("NETWORK", "Error al actualizar: ${response.status}")
                throw Exception("Error al actualizar: ${response.status}")
            }
        } catch (e: Exception) {
            Log.e("NETWORK", "Error al actualizar enfermera con ID ${updatedData.id}: ${e.localizedMessage}")
            throw e
        }
    }

    override suspend fun register(nurse: Nurse): Nurse {
        val response = client.post("/nurse/signin") {
            contentType(ContentType.Application.Json)
            setBody(nurse)
        }

        val responseBody: String = response.bodyAsText()
        if (responseBody.isNotEmpty()) {
            try {
                val jsonResponse: JsonObject = json.decodeFromString(responseBody)
                val prettyJson = json.encodeToString(jsonResponse)
                Log.e("NETWORK", prettyJson)
            } catch (e: Exception) {
                Log.e("NETWORK", "Error parsing JSON response: ${e.localizedMessage}")
            }
        } else {
            Log.e("NETWORK", "Empty response body")
        }

        if (response.status == HttpStatusCode.Created) {
            return nurse
        } else {
            val newNurse = Nurse(
                id = -1, name = "", surname = "", email = "", password = "",
                birthDate = Date(), registerDate = Date(), profileImage = ""
            )
            return newNurse
        }
    }
}
