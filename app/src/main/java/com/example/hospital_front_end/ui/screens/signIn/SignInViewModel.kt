package com.example.hospital_front_end.ui.screens.signIn

import android.util.Patterns
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.example.hospital_front_end.models.nurse.Nurse
import com.example.hospital_front_end.nurseRepository.NurseRepository
import java.util.Date

class SignInViewModel : ViewModel() {
    private val nurseRepository = NurseRepository()

    data class ValidationResult(val isValid: Boolean, val errorMessage: String)

    private val _authenticationState = MutableStateFlow<Boolean>(false)
    val authenticationState: StateFlow<Boolean> = _authenticationState.asStateFlow()

    private val _nameError = mutableStateOf<String?>(null)
    val nameError: State<String?> = _nameError

    private val _lastNameError = mutableStateOf<String?>(null)
    val lastNameError: State<String?> = _lastNameError

    private val _emailError = mutableStateOf<String?>(null)
    val emailError: State<String?> = _emailError

    private val _birthDateError = mutableStateOf<String?>(null)
    val birthDateError: State<String?> = _birthDateError

    private val _passwordError = mutableStateOf<String?>(null)
    val passwordError: State<String?> = _passwordError

    private val _confirmPasswordError = mutableStateOf<String?>(null)
    val confirmPasswordError: State<String?> = _confirmPasswordError

    fun signIn(name: String, lastName: String, email: String, birthDate: String, password1: String, password2: String,) {
        val nameValid = validateName(name)
        val lastNameValid = validateLastName(lastName)
        val emailValid = validateEmail(email)
        val birthDateValid = validateBirthDate(birthDate)
        val passwordsValid = validatePasswords(password1, password2)

        _nameError.value = nameValid.errorMessage
        _lastNameError.value = lastNameValid.errorMessage
        _emailError.value = emailValid.errorMessage
        _birthDateError.value = birthDateValid.errorMessage
        _passwordError.value = passwordsValid.errorMessage
        if (nameValid.isValid && lastNameValid.isValid && emailValid.isValid && birthDateValid.isValid && passwordsValid.isValid) {
            _authenticationState.value = true
        } else {
            _authenticationState.value = false
        }

    }

    // Función para agregar enfermera
    fun addNurseToRepository(name: String, lastName: String, email: String, birthDate: Date) {
        val newNurse = Nurse(
            id = 99, // Asignar un ID automáticamente o dejar que el repositorio lo gestione
            name = name,
            surname = lastName,
            email = email,
            registerDate = Date(), // Fecha actual de registro
            birthDate = birthDate
        )
        nurseRepository.addNurse(newNurse)
    }

    private fun validateName(name: String): ValidationResult {
        return if (name.isBlank()) {
            _nameError.value = "Name is required"
            ValidationResult(false, "Name is required")
        } else {
            _nameError.value = null
            ValidationResult(true, "")
        }
    }

    private fun validateLastName(lastName: String): ValidationResult {
        return if (lastName.isBlank()) {
            _lastNameError.value = "Last name is required"
            ValidationResult(false, "Last name is required")
        } else {
            _lastNameError.value = null
            ValidationResult(true, "")
        }
    }

    private fun validateEmail(email: String): ValidationResult {
        return if (email.isBlank()) {
            _emailError.value = "Email cannot be empty"
            ValidationResult(false, "Email is empty")
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailError.value = "Invalid email format"
            ValidationResult(false, "Invalid email format")
        } else {
            _emailError.value = null
            ValidationResult(true, "")
        }
    }

    private fun validateBirthDate(birthDate: String): ValidationResult {
        return if (birthDate.isBlank()) {
            _birthDateError.value = "Birth date is required"
            ValidationResult(false, "Birth date is required")
        } else {
            _birthDateError.value = null
            ValidationResult(true, "")
        }
    }

    private fun validatePasswords(password1: String, password2: String): ValidationResult {
        return if (password1.isBlank() || password2.isBlank()) {
            _passwordError.value = "Password cannot be empty"
            ValidationResult(false, "Password is empty")
        } else if (password1 != password2) {
            _passwordError.value = "Passwords do not match"
            ValidationResult(false, "Passwords do not match")
        } else {
            _passwordError.value = null
            _confirmPasswordError.value = null
            ValidationResult(true, "")
        }
    }
}

