package com.example.panacea.ui.screens.home


import android.util.Log
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

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state

    private val _data = MutableStateFlow(UiData())
    val data: StateFlow<UiData> = _data


    fun fetchHomeData() {
        viewModelScope.launch {
            _state.value.isLoading = true
            println("GETTING HOME DATA....")
            _data.value = UiData(
                nurseList = repository.getNurseList(),
                currentUser = repository.getCurrentNurse()
            )
            Log.d("HomeViewModel", "Nurse list: ${_data.value.nurseList}")
            _state.value = UiState(isLoading = false, onSuccess = true)
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