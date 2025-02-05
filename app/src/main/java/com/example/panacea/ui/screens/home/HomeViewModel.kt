package com.example.panacea.ui.screens.home


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.panacea.data.repositories.NurseRepositoryImpl
import kotlinx.coroutines.launch
import com.example.panacea.domain.models.nurse.Nurse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


class HomeViewModel(
    private val repository: NurseRepositoryImpl
) : ViewModel() {

    var state by mutableStateOf(UiState())
        private set

    var data by mutableStateOf(UiData())
        private set

    init {
        viewModelScope.launch {
            state = UiState(isLoading = true)
            data = UiData(
                nurseList = repository.getCachedNurseList(),
                currentUser = repository.getCurrentUser()
            )
            state = UiState(isLoading = false, onSuccess = true)
        }
    }

    data class UiState(
        var isLoading: Boolean = false,
        val onError: Boolean = false,
        var onSuccess: Boolean = false
    )

    data class UiData(
        val nurseList: List<Nurse> = emptyList(),
        val currentUser: Nurse? = null
    )

}