package com.example.panacea.koin

import com.example.panacea.data.network.NetworkServices
import com.example.panacea.data.repositories.NurseRepository
import com.example.panacea.ui.screens.directory.DirectoryViewModel
import com.example.panacea.ui.screens.home.HomeViewModel
import com.example.panacea.ui.screens.signIn.SignInViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

// Define el módulo de Koin
val appModule = module {

    // Proveer el ViewModel e inyectar el repositorio
    viewModel { SignInViewModel(get()) }
    viewModel { DirectoryViewModel(get()) }
    viewModel { HomeViewModel(get()) }

    // Repositorios
    singleOf(::NurseRepository)
    singleOf(::NetworkServices)
}

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(appModule)
    }
}