package com.example.panacea.ui.screens.login

import android.content.Context
import android.util.Patterns
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.panacea.data.repositories.NurseRepositoryImpl
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: NurseRepositoryImpl
) : ViewModel() {

    private var isBiometricAuthAvailable = false
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    private val _isLoading = mutableStateOf(false)
    private val _authenticationState = mutableStateOf(false)
    private val _isLoginSuccessful = mutableStateOf(false)
    private val _passwordError = mutableStateOf("")
    private val _emailError = mutableStateOf("")

    // ESTADOS
    val isLoading: State<Boolean> = _isLoading
    val authenticationState: State<Boolean> = _authenticationState
    val passwordError: State<String> = _passwordError
    val emailError: State<String> = _emailError

    fun setupAuth(context: Context) {

        if (BiometricManager.from(context)
                .canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL) == BiometricManager.BIOMETRIC_SUCCESS
        ) {
            isBiometricAuthAvailable = true
            promptInfo = BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric Authentication")
                .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL)
                .build()
        }
    }

    fun authenticate(context: FragmentActivity, onAuthenticationResult: (Boolean) -> Unit) {
        if (isBiometricAuthAvailable) {
            if (!this::promptInfo.isInitialized) {
                promptInfo =
                    BiometricPrompt.PromptInfo.Builder().setTitle("Biometric Authentication")
                        .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL)
                        .build()
            }

            val authenticationCallback = object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    onAuthenticationResult(true)
                    _authenticationState.value = true
                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    onAuthenticationResult(false)
                    _authenticationState.value = false
                }
            }

            BiometricPrompt(
                context, ContextCompat.getMainExecutor(context), authenticationCallback
            ).authenticate(promptInfo)

        } else {
            onAuthenticationResult(true)
            _authenticationState.value = true
        }
    }

    fun login(email: String, password: String) {
        val validationEmail = validateEmail(email)
        val validationPassword = validatePassword(password)

        println("Validando email: $email -> isValid: ${validationEmail.isValid}, error: ${validationEmail.errorMessage}")
        println("Validando password -> isValid: ${validationPassword.isValid}, error: ${validationPassword.errorMessage}")

        when {
            !validationEmail.isValid && !validationPassword.isValid -> {
                _emailError.value = validationEmail.errorMessage
                _passwordError.value = validationPassword.errorMessage
                println("Ambos son inválidos. _authenticationState: ${_authenticationState.value}")
            }

            validationEmail.isValid && !validationPassword.isValid -> {
                _passwordError.value = validationPassword.errorMessage
                println("Email válido pero password inválido. _authenticationState: ${_authenticationState.value}")
            }

            !validationEmail.isValid && validationPassword.isValid -> {
                _emailError.value = validationEmail.errorMessage
                println("Password válido pero email inválido. _authenticationState: ${_authenticationState.value}")
            }

            else -> {  // Email y Password son válidos

                println("Email y password válidos. Iniciando login...")

                viewModelScope.launch {

                    _isLoading.value = true
                    delay(50)
                    println("Llamando a validateLogin()... _isLoading: ${_isLoading.value}")

                    repository.validateLogin(email, password).collect { nurse ->
                        if(nurse != null){
                            _authenticationState.value = true
                            //_isLoginSuccessful.value = true
                        } else {
                            _emailError.value = " "
                            _passwordError.value = "Incorrect email or password"
                        }

                        println("Respuesta de validateLogin -> " +
                                "_authenticationState: ${_authenticationState.value}, " +
                                "_isLoginSuccessful: ${_isLoginSuccessful.value}, " +
                                "_isLoading: ${_isLoading.value}")
                    }

                    _isLoading.value = false
                }
            }
        }
    }


    private fun validateEmail(email: String): ValidationResult {
        return when {
            email.isBlank() -> {
                _emailError.value = "Email cannot be empty"
                ValidationResult(false, "Email is empty")
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                _emailError.value = "Invalid email format"
                ValidationResult(false, "Invalid email format")
            }
            else -> {
                _emailError.value = ""
                ValidationResult(true, "")
            }
        }
    }

    private fun validatePassword(password: String): ValidationResult {
        return when {
            password.isBlank() -> {
                _passwordError.value = "Password cannot be empty"
                ValidationResult(false, "Password is empty")
            }
            password.length < 4 -> {
                _passwordError.value = "Password must be at least 4 characters long"
                ValidationResult(false, "Password too short")
            }
            else -> {
                _passwordError.value = ""
                ValidationResult(true, "")
            }
        }
    }

    data class ValidationResult(val isValid: Boolean, val errorMessage: String)

}