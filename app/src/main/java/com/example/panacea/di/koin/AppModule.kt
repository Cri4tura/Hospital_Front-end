package com.example.panacea.di.koin

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
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.AttributeKey
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

    // HttpClient client singleton
    single {
        HttpClient(OkHttp) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
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
                connectTimeoutMillis = 10000
                requestTimeoutMillis = 10000
                socketTimeoutMillis = 10000
            }
            install(HttpRequestRetry) {
                maxRetries = 5

                retryIf { request, response ->
                    val currentRetryAttempt = request.attributes.getOrNull(RetryAttemptKey) ?: 0
                    println("Intento: $currentRetryAttempt, URL: ${request.url}, Código: ${response.status.value}")

                    // Detener el reintento si ya estamos en el último intento
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

            // Corrección: manejo de errores con los dos parámetros requeridos
            HttpResponseValidator {
                handleResponseExceptionWithRequest { exception, request ->
                    when (exception) {

                        is ConnectTimeoutException -> {
                            println("Timeout de conexión al servidor ${request.url}: ${exception.localizedMessage}")
                            throw ConnectionException(exception.localizedMessage!!, ErrorType.TIMEOUT)
                        }

                        is HttpRequestTimeoutException -> {
                            println("Servidor apagado ${request.url}: ${exception.localizedMessage}")
                            throw ConnectionException("Sin conexión a Internet", ErrorType.SERVER_DOWN)
                        }

                        is UnknownHostException -> {
                            println("No se puede resolver el host ${request.url}: ${exception.localizedMessage}")
                            throw ConnectionException("No se puede resolver el servidor", ErrorType.UNKNOWN_HOST)
                        }

                        is SocketTimeoutException -> {
                            println("Tiempo de espera agotado para la respuesta del servidor ${request.url}: ${exception.localizedMessage}")
                            throw ConnectionException("Tiempo de espera agotado", ErrorType.TIMEOUT)
                        }

                        is NoRouteToHostException -> {
                            println("No se puede acceder al servidor ${request.url}: ${exception.localizedMessage}")
                            throw ConnectionException("No hay ruta hacia el servidor", ErrorType.NO_ROUTE)
                        }

                        is SSLHandshakeException -> {
                            println("Error de SSL al conectar con ${request.url}: ${exception.localizedMessage}")
                            throw ConnectionException("Problema de seguridad al conectar con el servidor", ErrorType.SSL_ERROR)
                        }

                        is HttpException -> {
                            println("Error HTTP al conectar con ${request.url}: ${exception.localizedMessage}")
                            throw ConnectionException("Error HTTP: ${exception.localizedMessage}", ErrorType.HTTP_ERROR)
                        }

                        is kotlinx.io.IOException -> {
                            println("Error de entrada/salida al conectar con ${request.url}: ${exception.localizedMessage}")
                            throw ConnectionException("Error en la conexión: ${exception.localizedMessage}", ErrorType.IO_ERROR)
                        }

                        else -> {
                            println("Error al conectar: ${exception.localizedMessage}")
                            throw ConnectionException(exception.localizedMessage!!, ErrorType.UNKNOWN)
                        }
                    }
                }
            }
        }
    }

    // APIs
    single { NetworkServicesImpl(get()) }

    // Repositorios
    singleOf(::NurseRepositoryImpl) // Repositorio singleton

    // Proveer el ViewModel e inyectar el repositorio
    viewModel { NetworkViewModel(get()) }
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