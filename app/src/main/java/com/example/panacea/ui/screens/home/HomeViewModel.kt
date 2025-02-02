package com.example.panacea.ui.screens.home

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.panacea.data.repositories.NurseRepositoryImpl
import kotlinx.coroutines.launch
import com.example.panacea.domain.models.nurse.Nurse
import com.example.panacea.ui.screens.profile.ProfileViewModel.UiData
import com.example.panacea.ui.screens.profile.ProfileViewModel.UiState
import kotlinx.coroutines.delay
import java.io.IOException

class HomeViewModel(
    private val repository: NurseRepositoryImpl
) : ViewModel() {

    var state by mutableStateOf(UiState())
        private set

    var data by mutableStateOf(UiData())
        private set

    init {
        fetchHomeData()
    }

    private fun fetchHomeData() {
        viewModelScope.launch {
            state = UiState(isLoading = true)
            println("GETTING HOME DATA....")
            try {
                data = UiData(
                    nurseList = repository.getNurseList(),
                    currentUser = repository.getCurrentNurse()
                )
                state = UiState(onSuccess = true)
            } catch (e: IOException) {
                println("Network error: ${e.message}")
                state = UiState(onError = true)
            } catch (e: Exception) {
                println("Unexpected error: ${e.message}")
                state = UiState(onError = true)
            } finally {
                state = state.copy(isLoading = false)
            }
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