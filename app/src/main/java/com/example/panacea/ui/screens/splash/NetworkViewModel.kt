package com.example.panacea.ui.screens.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.ktor.client.HttpClient
import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.request.get
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.system.exitProcess

class NetworkViewModel(private val client: HttpClient) : ViewModel() {

    // Estado para manejar los errores de conexión
    private val _connectionError = MutableLiveData<String?>()
    val connectionError: LiveData<String?> get() = _connectionError

    // Estado de carga (isLoading)
    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    init {
        fetchData()
    }

    // Función que simula un reintento de conexión
    private fun fetchData() {
        val retryDelay = 2000L // 2 segundos de espera entre reintentos
        var isSuccess = false

        viewModelScope.launch {
            while (!isSuccess) {
                try {
                    _isLoading.postValue(true)
                    val response = client.get("/nurse")
                    println("Respuesta recibida: $response")
                    // Si la respuesta es exitosa, actualizamos el estado
                    // Aquí puedes manejar el éxito, p.ej., actualizando el UI
                    isSuccess = true
                    _isLoading.postValue(false)
                    onConnectionSuccess()
                } catch (e: Exception) {
                    println("Retrying connection --> Intento fallido: ${e.localizedMessage}")
                    delay(retryDelay) // Espera antes de reintentar
                    _isLoading.postValue(false) // Finaliza la carga
                    _connectionError.postValue("${e.localizedMessage}")
                    break
                }
            }
        }
    }

    // Función para indicar que la conexión fue exitosa
    fun onConnectionSuccess() {
        _connectionError.postValue(null)  // Se indica que la conexión fue exitosa
    }

    // funcion para actualizar el estado cuando ocurre un error de conexión
    private fun onConnectionError() {
        // Establecemos el mensaje de error
        _connectionError.postValue("Se alcanzó el máximo de intentos. No se puede conectar.")

        // Cerrar la aplicación de forma abrupta
        exitProcess(0)
    }

    // funcion para restablecer la conexión
    fun onReconnect() {
        // Restablecemos el estado de error
        _connectionError.postValue(null)

        // Llamamos a la función que reintenta la conexión
        fetchData()
    }
}