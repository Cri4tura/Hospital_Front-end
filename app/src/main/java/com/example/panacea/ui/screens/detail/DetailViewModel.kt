package com.example.panacea.ui.screens.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.panacea.data.models.nurse.Nurse
import com.example.panacea.data.repositories.NurseRepository
import kotlinx.coroutines.launch

class DetailViewModel (
    private val nurseId: Int,
    private val repository: NurseRepository
) : ViewModel() {

    var state by mutableStateOf(UiState())
        private set

    init {
        viewModelScope.launch {
            state = UiState(isLoading = true)
//            repository.getMovieById(movieId).collect {
//                it?.let { state = UiState(isLoading = false, movie = it)}
//            }
            state = UiState(isLoading = false, nurse = repository.getNurseByID(nurseId))
        }
    }

    data class UiState(
        val isLoading: Boolean = false,
        val nurse: Nurse? = null
    )
}