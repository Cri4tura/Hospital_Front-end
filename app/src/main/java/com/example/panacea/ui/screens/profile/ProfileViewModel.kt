package com.example.panacea.ui.screens.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.panacea.data.models.nurse.Nurse
import com.example.panacea.data.repositories.NurseRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ProfileViewModel (
    private val repository: NurseRepository
) : ViewModel() {

    var state by mutableStateOf(UiState())
        private set

    var data by mutableStateOf(UiData())

    init {
        viewModelScope.launch {
            state = UiState(isLoading = true)
            println("GETING DATA USER....")
            delay(50)
            val user = repository.getCurrentNurse()
            repository.remoteNurses.collect {
                if (it.isEmpty()) {
                    state = UiState(onError = true)
                } else {
                    state = UiState(onSuccess = true)
                    data = UiData(nurseList = it, currentUser = user)
                }
            }
            state = UiState(isLoading = false)
        }
    }

    data class UiState(
        val isLoading: Boolean = false,
        val onError: Boolean = false,
        val onSuccess: Boolean = false
    )

    data class UiData(
        val nurseList: List<Nurse> = emptyList(),
        val currentUser: Nurse? = null
    )
}