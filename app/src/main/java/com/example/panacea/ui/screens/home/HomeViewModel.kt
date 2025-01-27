package com.example.panacea.ui.screens.home

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText

class HomeViewModel {
    private val client = HttpClient()

    suspend fun greeting(): String {
        val response = client.get("http://localhost:8080/nurse")

        return response.bodyAsText()
    }
}