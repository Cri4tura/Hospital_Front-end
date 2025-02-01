package com.example.panacea.ui.screens.directory

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.panacea.domain.models.nurse.Nurse
import com.example.panacea.data.repositories.NurseRepositoryImpl
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DirectoryViewModel(
    private val repository: NurseRepositoryImpl
) : ViewModel() {

    var state by mutableStateOf(UiState())
        private set

    var data by mutableStateOf(UiData())

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