package com.example.panacea.ui.screens.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.panacea.domain.models.nurse.Nurse
import com.example.panacea.data.repositories.NurseRepositoryImpl
import kotlinx.coroutines.launch

class DetailViewModel(
    private val nurseId: Int,
    private val repository: NurseRepositoryImpl
) : ViewModel() {

    var state by mutableStateOf(UiState())
        private set

    var data by mutableStateOf(UiData())
        private set

    init {
        viewModelScope.launch {
            state = UiState(isLoading = true)
            val nurseList = repository.getCachedNurseList()
            val nurseToShow = nurseList.find { it.id == nurseId }
            data = UiData(nurse = nurseToShow)
            state = UiState(isLoading = false)
        }
    }

    data class UiState(
        val isLoading: Boolean = false,
    )

    data class UiData(
        val nurse: Nurse? = null,
    )
}