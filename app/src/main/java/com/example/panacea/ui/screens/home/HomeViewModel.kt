package com.example.panacea.ui.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.panacea.data.repositories.NurseRepository
import kotlinx.coroutines.launch
import com.example.panacea.data.models.nurse.Nurse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel (
    private val repository: NurseRepository
) : ViewModel() {

    private val _currentNurse = MutableStateFlow<Nurse?>(null)
    val currentNurse: StateFlow<Nurse?> = _currentNurse

    var state by mutableStateOf(UiState())
        private set

    init {
        viewModelScope.launch {
            _currentNurse.value = repository.getCurrentNurse()
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