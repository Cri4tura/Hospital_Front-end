package com.example.hospital_front_end.koin

import com.example.hospital_front_end.nurseRepository.NurseRepository
import com.example.hospital_front_end.ui.screens.nurseInfo.FindByNameView
import com.example.hospital_front_end.ui.screens.nurseInfo.FindByNameViewModel
import com.example.hospital_front_end.ui.screens.signIn.SignInViewModel
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel

// Define el módulo de Koin
val appModule = module {
    // Proveer el repositorio como singleton
    single { NurseRepository() }

    // Proveer el ViewModel e inyectar el repositorio
    viewModel { SignInViewModel(get()) }
    viewModel { FindByNameViewModel(get()) }
}