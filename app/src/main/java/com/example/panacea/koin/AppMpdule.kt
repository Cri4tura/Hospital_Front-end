package com.example.panacea.koin

import com.example.panacea.data.repositories.NurseRepository
import com.example.panacea.ui.screens.directory.DirectoryViewModel
import com.example.panacea.ui.screens.signIn.SignInViewModel
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel

// Define el módulo de Koin
val appModule = module {

    // Proveer el repositorio como singleton
    single { NurseRepository() }

    // Proveer el ViewModel e inyectar el repositorio
    viewModel { SignInViewModel(get()) }
    viewModel { DirectoryViewModel(get()) }
}