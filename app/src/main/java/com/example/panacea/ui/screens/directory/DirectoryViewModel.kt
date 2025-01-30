package com.example.panacea.ui.screens.directory

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.panacea.data.models.nurse.Nurse
import com.example.panacea.data.repositories.NurseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DirectoryViewModel(
    private val repository: NurseRepository
) : ViewModel() {

    var state by mutableStateOf(UiState())
        private set

    init {
        viewModelScope.launch {
            state = UiState(isLoading = true)
            repository.remoteNurses.collect {
                if (it.isNotEmpty()) {
                    state = UiState(isLoading = false, nurseList = it)
                }
            }
        }
    }

    data class UiState(
        val isLoading: Boolean = false,
        val nurseList: List<Nurse> = emptyList()
    )
}