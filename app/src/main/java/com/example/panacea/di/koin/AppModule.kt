package com.example.panacea.di.koin

import android.content.ContentValues.TAG
import android.util.Log
import com.bumptech.glide.load.HttpException
import com.example.panacea.data.network.ConnectionException
import com.example.panacea.data.network.ErrorType
import com.example.panacea.data.network.NetworkServicesImpl
import com.example.panacea.data.repositories.NurseRepositoryImpl
import com.example.panacea.ui.screens.directory.DirectoryViewModel
import com.example.panacea.ui.screens.home.HomeViewModel
import com.example.panacea.ui.screens.login.LoginViewModel
import com.example.panacea.ui.screens.profile.ProfileViewModel
import com.example.panacea.ui.screens.signIn.SignInViewModel
import com.example.panacea.ui.screens.splash.NetworkViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.statement.content
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.AttributeKey
import io.ktor.utils.io.InternalAPI
import kotlinx.serialization.json.Json
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent.getKoin
import java.net.NoRouteToHostException
import java.net.UnknownHostException
import javax.net.ssl.SSLHandshakeException

// Clave personalizada para rastrear el número de intentos
val RetryAttemptKey = AttributeKey<Int>("RetryAttemptKey")

// Define el módulo de Koin
val appModule = module {

    // Configuración de Json con prettyPrint
    single {
        Json { prettyPrint = true; ignoreUnknownKeys = true }
    }

    // HttpClient client singleton
    single {
        HttpClient(OkHttp) {
            val json = get<Json>()
            install(ContentNegotiation) {
                json(json)
            }
            install(Logging) {
                level = LogLevel.BODY
            }
            install(DefaultRequest) {
                url {
                    protocol = URLProtocol.HTTP
                    host = "192.168.1.134"
                    port = 8080
                }
            }
            install(HttpTimeout) {
                connectTimeoutMillis = 5000
                requestTimeoutMillis = 5000
                socketTimeoutMillis = 5000
            }
            install(HttpRequestRetry) {
                maxRetries = 1
                retryIf { request, response ->
                    Log.d(TAG,"START ${request.method.value}, URL: ${request.url}  " +
                             "--->\nSTATUS CODE: ${response.status.value}")
                    // Detener el reintento si ya estamos en el último intento
                    val currentRetryAttempt = request.attributes.getOrNull(RetryAttemptKey) ?: 0
                    if (currentRetryAttempt >= maxRetries) {
                        println("Último intento alcanzado, no se reintentará más.")
                        return@retryIf false
                    }
                    response.status.value >= 500 // Solo reintentar en errores de servidor (5xx)
                }
                // Incrementar el contador en cada reintento
                modifyRequest { request ->
                    val currentAttempt = request.attributes.getOrNull(RetryAttemptKey) ?: 0
                    request.attributes.put(RetryAttemptKey, currentAttempt + 1)
                }
                // Delay incremental entre reintentos (1s, 2s, 3s)
                delayMillis { retry -> retry * 1000L }
            }
            // Manejo de errores con los dos parámetros requeridos
            HttpResponseValidator {
                handleResponseExceptionWithRequest { exception, request ->
                    when (exception) {
                        is ConnectTimeoutException -> {
                            throw ConnectionException(
                                exception.localizedMessage!!,
                                ErrorType.TIMEOUT
                            )
                        }

                        is HttpRequestTimeoutException -> {
                            throw ConnectionException(
                                "Sin conexión al servidor",
                                ErrorType.SERVER_DOWN
                            )
                        }

                        is UnknownHostException -> {
                            throw ConnectionException(
                                "No se puede resolver el servidor",
                                ErrorType.UNKNOWN_HOST
                            )
                        }

                        is SocketTimeoutException -> {
                            throw ConnectionException(
                                "Tiempo de espera agotado",
                                ErrorType.TIMEOUT
                            )
                        }

                        is NoRouteToHostException -> {
                            throw ConnectionException(
                                "No hay ruta hacia el servidor",
                                ErrorType.NO_ROUTE
                            )
                        }

                        is SSLHandshakeException -> {
                            throw ConnectionException(
                                "Problema de seguridad al conectar con el servidor",
                                ErrorType.SSL_ERROR
                            )
                        }

                        is HttpException -> {
                            throw ConnectionException(
                                "Error HTTP: ${exception.localizedMessage}",
                                ErrorType.HTTP_ERROR
                            )
                        }

                        is kotlinx.io.IOException -> {
                            throw ConnectionException(
                                "Error en la conexión: ${exception.localizedMessage}",
                                ErrorType.IO_ERROR
                            )
                        }

                        else -> {
                            throw ConnectionException(
                                exception.localizedMessage!!,
                                ErrorType.UNKNOWN
                            )
                        }
                    }
                }
            }
        }
    }

    // APIs
    single { NetworkServicesImpl(get(), get()) }

    // Repositorios
    singleOf(::NurseRepositoryImpl) // Repositorio singleton

    // Proveer el ViewModel e inyectar el repositorio
    viewModel { NetworkViewModel(get(), get()) }
    viewModel { SignInViewModel(get()) }
    viewModel { ProfileViewModel(get()) }
    viewModel { DirectoryViewModel(get()) }
    viewModel { HomeViewModel(get()) }
    viewModel { LoginViewModel(get()) }
}

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(appModule)
    }

    //val networkServices: NetworkServicesImpl = getKoin().get()
    //Log.d(TAG, "CLIENTE HTTP INYECTADO en NetworkServices: $networkServices")

    //val viewModelProfile: ProfileViewModel = getKoin().get()
    //Log.d(TAG, "VIEWMODEL INYECTADO en ProfileViewModel: $viewModelProfile")
}