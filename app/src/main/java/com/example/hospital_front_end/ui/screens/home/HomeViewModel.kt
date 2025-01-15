package com.example.hospital_front_end.ui.screens.home

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText

class HomeViewModel {
    private val client = HttpClient()

    suspend fun greeting(): String {
        println("RESPONSE")
        val response = client.get("http://10.0.2.2:8080/nurse")


        println(response.bodyAsText())

        return response.bodyAsText()
    }
}