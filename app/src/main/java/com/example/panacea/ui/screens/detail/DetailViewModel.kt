package com.example.panacea.ui.screens.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.panacea.data.models.nurse.Nurse
import com.example.panacea.data.repositories.NurseRepository
import com.example.panacea.ui.screens.home.HomeViewModel.UiState
import kotlinx.coroutines.launch

class DetailViewModel(
    private val nurseId: Int,
    private val repository: NurseRepository
) : ViewModel() {

    var state by mutableStateOf(UiState())
        private set

    init {
        viewModelScope.launch {
            state = UiState(isLoading = true)
            repository.getNurseById(nurseId).collect {
                state = UiState(isLoading = false, nurse = it)
            }
        }
    }

    data class UiState(
        val isLoading: Boolean = false,
        val nurse: Nurse? = null
    )
}