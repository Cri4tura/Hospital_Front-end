package com.example.panacea.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.panacea.models.nurse.Nurse
import com.example.panacea.nurseRepository.NurseRepository
import com.example.panacea.utils.Constants
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
/*
class NavigationController(private val nurseRepository: NurseRepository) : ViewModel() {

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

    fun navigateToDirectory() {
        viewModelScope.launch {
            _navigationEvent.emit(Constants.NavigationEvent.NavigateToDirectory)
        }
    }

    fun navigateBack() {
        viewModelScope.launch {
            _navigationEvent.emit(Constants.NavigationEvent.NavigateBack)
        }
    }

    fun navigateToDetail(nurse: Nurse) {
        selectedNurse = nurse
        viewModelScope.launch {
            _navigationEvent.emit(Constants.NavigationEvent.NavigateToDetail)
        }
    }

    fun navigateToProfile() {
        viewModelScope.launch {
            _navigationEvent.emit(Constants.NavigationEvent.NavigateToProfile)
        }
    }

    fun navigateToDocuments(){
        viewModelScope.launch {
            _navigationEvent.emit(Constants.NavigationEvent.NavigateToDocuments)
        }
    }

    fun navigateToNews(){
        viewModelScope.launch {
            _navigationEvent.emit(Constants.NavigationEvent.NavigateToNews)
        }
    }

    fun navigateToHistory() {
        viewModelScope.launch {
            _navigationEvent.emit(Constants.NavigationEvent.NavigateToHistory)
        }
    }

}

 */