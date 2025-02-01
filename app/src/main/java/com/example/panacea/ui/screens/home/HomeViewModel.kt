package com.example.panacea.ui.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.panacea.data.repositories.NurseRepository
import kotlinx.coroutines.launch
import com.example.panacea.data.models.nurse.Nurse
import kotlinx.coroutines.delay

class HomeViewModel(
    private val repository: NurseRepository
) : ViewModel() {

    var state by mutableStateOf(UiState())
        private set

    var data by mutableStateOf(UiData())
        private set

    init {
        viewModelScope.launch {
            state = UiState(isLoading = true)
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