package com.example.panacea.ui.screens.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.panacea.domain.models.nurse.Nurse
import com.example.panacea.data.repositories.NurseRepositoryImpl
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val repository: NurseRepositoryImpl
) : ViewModel() {

    var state by mutableStateOf(UiState())
        private set

    var data by mutableStateOf(UiData())

    init {
        viewModelScope.launch {
            state = UiState(isLoading = true)
            println("GETING DATA USER....")
            data = UiData(repository.getCurrentNurse())
            state = UiState(isLoading = false)
        }
    }

    fun deleteAccount(userId: Int) {
        viewModelScope.launch {
            state = UiState(isLoading = true)
            println("DELETING DATA USER....")
            repository.deleteAccount(userId).collect{
                state = state.copy(isLoading = false, isDeleted = true)
            }
        }
    }

    fun updateProfileImage(uri: android.net.Uri) {

    }

    data class UiState(
        val isLoading: Boolean = false,
        val isDeleted: Boolean = false,
        val onError: Boolean = false,
        val onSuccess: Boolean = false,
    )

    data class UiData(
        val currentUser: Nurse? = null
    )
}