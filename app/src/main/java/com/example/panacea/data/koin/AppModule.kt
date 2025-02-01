package com.example.panacea.data.koin

import android.util.Log
import com.example.panacea.data.network.NetworkServices
import com.example.panacea.data.repositories.NurseRepository
import com.example.panacea.ui.screens.directory.DirectoryViewModel
import com.example.panacea.ui.screens.home.HomeViewModel
import com.example.panacea.ui.screens.login.LoginViewModel
import com.example.panacea.ui.screens.profile.ProfileViewModel
import com.example.panacea.ui.screens.signIn.SignInViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent.getKoin

// Define el módulo de Koin
val appModule = module {

    single {
        HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }
            install(DefaultRequest) {
                url {
                    protocol = URLProtocol.HTTP
                    host = "192.168.1.134"
                    port = 8080
                }
            }
        }
    }

    // APIs
    single { NetworkServices(get()) }

    // Repositorios
    singleOf(::NurseRepository)

    // Proveer el ViewModel e inyectar el repositorio
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

    val networkServices: NetworkServices = getKoin().get()
    Log.i("NETWORK", "CLIENTE HTTP inyectado en NetworkServices: $networkServices")

    val viewModelProfile: ProfileViewModel = getKoin().get()
    Log.i("VIEWMODEL", "VM inyectado en ProfileViewModel: $viewModelProfile")
}