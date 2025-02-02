package com.example.panacea.data.network

class ConnectionException(
    message: String,
    val type: ErrorType,
    val errorCode: Int? = null,  // Puede ser un código HTTP o cualquier otro código de error
    override val cause: Throwable? = null  // Causa original de la excepción (opcional)
) : Exception(message, cause) {

    override fun toString(): String {
        val baseMessage = "Error Type: $type, Message: $message"
        return if (errorCode != null) {
            "$baseMessage, ErrorCode: $errorCode"
        } else {
            baseMessage
        }
    }
}

enum class ErrorType {
    TIMEOUT,            // Tiempo de espera agotado
    SERVER_DOWN,        // El servidor está apagado o no disponible
    UNKNOWN,            // Error genérico o desconocido
    UNKNOWN_HOST,       // No se puede resolver el nombre del host
    NO_ROUTE,           // No hay ruta hacia el host
    SSL_ERROR,          // Error durante el handshake SSL
    HTTP_ERROR,         // Error HTTP (códigos de error del servidor)
    IO_ERROR            // Error de entrada/salida general
}
