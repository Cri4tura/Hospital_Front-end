package com.example.panacea.ui.screens.directory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.panacea.models.nurse.Nurse
import com.example.panacea.nurseRepository.NurseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DirectoryViewModel(private val nurseRepository: NurseRepository) : ViewModel() {

    private val _nurseList = MutableStateFlow<List<Nurse>>(emptyList())
    val nurseList: StateFlow<List<Nurse>> = _nurseList

    init {
        // Recuperar la lista de enfermeras desde el repositorio al iniciar el ViewModel
        viewModelScope.launch {
            _nurseList.value = nurseRepository.getNurseList()
        }
    }
}