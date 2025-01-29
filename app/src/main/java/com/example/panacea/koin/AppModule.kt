package com.example.panacea.koin

import com.example.panacea.data.network.NetworkServices
import com.example.panacea.data.repositories.NurseRepository
import com.example.panacea.ui.screens.directory.DirectoryViewModel
import com.example.panacea.ui.screens.home.HomeViewModel
import com.example.panacea.ui.screens.signIn.SignInViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.http.URLProtocol
import kotlinx.serialization.json.Json
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.KoinAppDeclaration


// Define el módulo de Koin
val appModule = module {

    // Proveer el ViewModel e inyectar el repositorio
    viewModel { SignInViewModel(get()) }
    viewModel { DirectoryViewModel(get()) }

}

val dataModule = module {
    factoryOf(::NurseRepository)
    factoryOf(::NetworkServices)
}

val viewModelsModule = module {
    viewModelOf(::HomeViewModel)
}

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules( dataModule, appModule, viewModelsModule)
    }
}