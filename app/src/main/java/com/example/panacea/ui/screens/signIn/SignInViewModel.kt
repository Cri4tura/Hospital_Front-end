package com.example.panacea.ui.screens.signIn

import android.util.Patterns
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.example.panacea.domain.models.nurse.Nurse
import com.example.panacea.data.repositories.NurseRepositoryImpl
import java.text.SimpleDateFormat
import androidx.compose.runtime.State
import android.util.Patterns
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.util.Date
import java.util.Locale

class SignInViewModel(private val nurseRepository: NurseRepositoryImpl) : ViewModel() {

    data class ValidationResult(val isValid: Boolean, val errorMessage: String)

    private val _authenticationState = MutableStateFlow(false)
    val authenticationState: StateFlow<Boolean> = _authenticationState.asStateFlow()

    private val _nameError = mutableStateOf<String?>(null)
    val nameError: State<String?> = _nameError

    private val _surnameError = mutableStateOf<String?>(null)
    val surnameError: State<String?> = _surnameError

    private val _emailError = mutableStateOf<String?>(null)
    val emailError: State<String?> = _emailError

    private val _birthDateError = mutableStateOf<String?>(null)
    val birthDateError: State<String?> = _birthDateError

    private val _passwordError = mutableStateOf<String?>(null)
    val passwordError: State<String?> = _passwordError

    private val _confirmPasswordError = mutableStateOf<String?>(null)


    fun signIn(
        name: String,
        lastName: String,
        email: String,
        birthDate: String,
        password1: String,
        password2: String,
    ) {
        val nameValid = validateName(name)
        val lastNameValid = validateSurname(lastName)
        val emailValid = validateEmail(email)
        val birthDateValid = validateBirthDate(birthDate)
        val birthDateParsed = parseBirthDate(birthDate)
        val passwordsValid = validatePasswords(password1, password2)

        println("Validando nombre: $name -> isValid: ${nameValid.isValid}, error: ${nameValid.errorMessage}")
        println("Validando apellido(s): $lastName -> isValid: ${lastNameValid.isValid}, error: ${lastNameValid.errorMessage}")
        println("Validando email: $email -> isValid: ${emailValid.isValid}, error: ${emailValid.errorMessage}")
        println("Validando birth date: $birthDate -> isValid: ${birthDateValid.isValid}, error: ${birthDateValid.errorMessage}")
        println("Validando password: $password1 -> isValid: ${passwordsValid.isValid}, error: ${passwordsValid.errorMessage}")

        println("DOB: $birthDateParsed")
        _nameError.value = nameValid.errorMessage
        _surnameError.value = lastNameValid.errorMessage
        _emailError.value = emailValid.errorMessage
        _birthDateError.value = birthDateValid.errorMessage
        _passwordError.value = passwordsValid.errorMessage
        _authenticationState.value = nameValid.isValid && lastNameValid.isValid && emailValid.isValid && birthDateValid.isValid && passwordsValid.isValid

            if (_authenticationState.value) {
                val newNurse = Nurse(
                    id = 99,
                    name = name,
                    surname = lastName,
                    email = email,
                    registerDate = Date(),
                    birthDate = birthDateParsed!!,
                    password = password1
                )


                viewModelScope.launch {
                    val result = nurseRepository.signinNurse(newNurse)
                    when (result) {
                        is Flow<Boolean> -> result.collect { success -> _authenticationState.value = success }

                        else -> throw IllegalStateException("signinNurse debe devolver Flow<Boolean>")
                    }
                }
            }
        }

    private fun validateName(name: String): ValidationResult {
        return when {
            name.isBlank() -> {
                _nameError.value = "Name is empty"
                ValidationResult(false, "Name is empty")
            }
            else -> {
                _nameError.value = null
                ValidationResult(true, "")
            }
        }
    }

    private fun validateSurname(surname: String): ValidationResult {
        return when {
            surname.isBlank() -> {
                _surnameError.value = "Name is empty"
                ValidationResult(false, "Name is empty")
            }

            else -> {
                ValidationResult(true, "")
            }
        }
    }

    private fun validateEmail(email: String): ValidationResult {
        return when {
            email.isBlank() -> {
                _emailError.value = "Email is empty"
                ValidationResult(
                    false,
                    "Email is empty"
                )
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                _emailError.value = "Invalid email format"
                ValidationResult(
                    false,
                    "Invalid email format"
                )
            }
            else -> {
                _emailError.value = null
                ValidationResult(
                    true,
                    ""
                )
            }
        }
    }

    private fun validateBirthDate(birthDate: String): ValidationResult {
        return when {
            birthDate.isBlank() -> {
                _birthDateError.value = "Birth date is empty"
                ValidationResult(false, "Birth date is empty")
            }
            else -> {
                _birthDateError.value = birthDate
                ValidationResult(true, "")
            }
        }
    }

    private fun validatePasswords(password1: String, password2: String): ValidationResult {
        return when {
            password1.isBlank() -> {
                _passwordError.value = "Password is empty"
                ValidationResult(false, "Password is empty")
            }
            password2.isBlank() -> {
                _confirmPasswordError.value = "Password is empty"
                ValidationResult(false, "Password is empty")
            }
            password1 != password2 -> {
                _passwordError.value = "Passwords do not match"
                _confirmPasswordError.value = "Passwords do not match"
                ValidationResult(false, "Passwords do not match")
            }
            else -> {
                _passwordError.value = null
                _confirmPasswordError.value = null
                ValidationResult(true, "")
            }
        }
    }

    private fun parseBirthDate(birthDate: String): Date? {
        return try {
            val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            format.parse(birthDate)
        } catch (e: Exception) {
            null
        }
    }
}

