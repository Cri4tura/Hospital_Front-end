package com.example.panacea.ui.screens.splash

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.panacea.data.network.ConnectionException
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.head
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlin.system.exitProcess

class NetworkViewModel(private val json: Json, private val client: HttpClient) : ViewModel() {

    // Estado para manejar los errores de conexión
    private val _connectionError = MutableLiveData<String?>()
    val connectionError: LiveData<String?> get() = _connectionError

    // Estado de carga (isLoading)
    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> get() = _isLoading


    // Función que simula un reintento de conexión
    fun ping() {
        val retryDelay = 5000L // 5 segundos de espera entre reintentos
        var isSuccess = false

        viewModelScope.launch {
            while (!isSuccess) {
                try {
                    _isLoading.postValue(true)

                    // TODO: HARD INTERNET LOAD
                    // val response = client.get("/nurse")
                    val response: HttpResponse = client.get("nurse/ping")

                    val responseBody: String = response.bodyAsText()
                    if (responseBody.isNotEmpty()) {
                        try {
                            val jsonResponse: JsonObject = json.decodeFromString(responseBody)
                            val prettyJson = json.encodeToString(jsonResponse)
                            Log.d(TAG, "JSON:\n$prettyJson")
                        } catch (e: Exception) {
                            Log.d(TAG, "JSON ERR: ${e.localizedMessage}")
                        }
                    } else {
                        Log.e(TAG, "JSON: Empty response body")
                    }
                    if(response.status == HttpStatusCode.OK){
                        isSuccess = true
                        onConnectionSuccess()
                        _isLoading.postValue(false)
                    }

                } catch (e: ConnectionException) {
                    Log.e("NETWORK", "CUSTOM EXCEPTION: ${e.localizedMessage}")
                    delay(retryDelay) // Espera antes de reintentar
                    _isLoading.postValue(false) // Finaliza la carga
                    _connectionError.postValue("Sin conexión")
                    break
                }
            }
        }
    }

    // Función para indicar que la conexión fue exitosa
    private fun onConnectionSuccess() {
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
        ping()
    }
}