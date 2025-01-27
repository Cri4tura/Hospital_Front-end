package com.example.panacea.models.user

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val email: String,
    val id: String = "",
    val password: String,
    val registerDate: String? = "",
    val userName: String? = "Guest",
    val userPhoto: String? = ""
) {
    init {
        require(email.contains("@")) { "El email debe ser válido" }
        require(password.length >= 3) { "La contraseña debe tener al menos 3 caracteres" }
    }
}