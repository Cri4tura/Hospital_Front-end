package com.example.hospital_front_end.ui.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hospital_front_end.model.nurse.Nurse
import com.example.hospital_front_end.nurseRepository.NurseRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


sealed class NavigationEvent {
    object NavigateToHome : NavigationEvent()
    object NavigateToLogin : NavigationEvent()
    object NavigateToRegister : NavigationEvent()
    object NavigateToNurseList : NavigationEvent()
    object NavigateToFindByName : NavigationEvent()
    object NavigateBack : NavigationEvent()
}

class NavigationViewModel(nurseList: List<Nurse>) : ViewModel() {

    private val nurseRepository = NurseRepository()

    private val _nurseList = MutableStateFlow<List<Nurse>>(emptyList())
    val nurseList: StateFlow<List<Nurse>> = _nurseList.asStateFlow()

    private val _navigationEvent = MutableSharedFlow<NavigationEvent>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    init {
        viewModelScope.launch {
            _nurseList.value = nurseRepository.getNurseList()
        }
    }

    fun navigateToHome() {
        viewModelScope.launch {
            _navigationEvent.emit(NavigationEvent.NavigateToHome)
        }
    }
    fun navigateToLogin() {
        viewModelScope.launch {
            _navigationEvent.emit(NavigationEvent.NavigateToLogin)
        }
    }
    fun navigateToSignIn() {
        viewModelScope.launch {
            _navigationEvent.emit(NavigationEvent.NavigateToRegister)
        }
    }
    fun navigateToNurseList() {
        viewModelScope.launch {
            _navigationEvent.emit(NavigationEvent.NavigateToNurseList)
        }
    }
    fun navigateToFindByName() {
        viewModelScope.launch {
            _navigationEvent.emit(NavigationEvent.NavigateToFindByName)
        }
    }
    fun navigateBack() {
        viewModelScope.launch {
            _navigationEvent.emit(NavigationEvent.NavigateBack)
        }

    }
}