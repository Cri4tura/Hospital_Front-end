package com.example.hospital_front_end.ui.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hospital_front_end.models.nurse.Nurse
import com.example.hospital_front_end.nurseRepository.NurseRepository
import com.example.hospital_front_end.utils.Constants
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class NavigationViewModel(private val nurseRepository: NurseRepository) : ViewModel() {

    lateinit var selectedNurse: Nurse

    private val _nurseList = MutableStateFlow<List<Nurse>>(emptyList())
    val nurseList: StateFlow<List<Nurse>> get() = _nurseList

    private val _navigationEvent = MutableSharedFlow<Constants.NavigationEvent>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    init {
        viewModelScope.launch {
            _nurseList.value = nurseRepository.getNurseList()
        }
    }

    fun addNurse(nurse: Nurse) {
        nurseRepository.addNurse(nurse)
        _nurseList.value = nurseRepository.getNurseList() // Actualiza la lista observada
    }

    fun navigateToHome() {
        viewModelScope.launch {
            _navigationEvent.emit(Constants.NavigationEvent.NavigateToHome)
        }
    }

    fun navigateToLogin() {
        viewModelScope.launch {
            _navigationEvent.emit(Constants.NavigationEvent.NavigateToLogin)
        }
    }

    fun navigateToSignIn() {
        viewModelScope.launch {
            _navigationEvent.emit(Constants.NavigationEvent.NavigateToRegister)
        }
    }

    fun navigateToNurseList() {
        viewModelScope.launch {
            _navigationEvent.emit(Constants.NavigationEvent.NavigateToNurseList)
        }
    }

    fun navigateToFindByName() {
        viewModelScope.launch {
            _navigationEvent.emit(Constants.NavigationEvent.NavigateToFindByName)
        }
    }

    fun navigateBack() {
        viewModelScope.launch {
            _navigationEvent.emit(Constants.NavigationEvent.NavigateBack)
        }
    }

    fun navigateToProfile(nurse: Nurse) {
        selectedNurse = nurse
        viewModelScope.launch {
            _navigationEvent.emit(Constants.NavigationEvent.NavigateToProfile)
        }

    }
}