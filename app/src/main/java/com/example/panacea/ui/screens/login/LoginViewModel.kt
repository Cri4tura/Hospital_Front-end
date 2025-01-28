package com.example.panacea.ui.screens.login

import android.content.Context
import android.util.Patterns
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.panacea.data.models.user.User
import com.example.panacea.utils.Constants
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.client.statement.request
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class LoginViewModel() : ViewModel() {

    private val client = HttpClient()

    private val _userState = MutableStateFlow<User?>(null)
    val userState: StateFlow<User?> = _userState

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _isLoginSuccessful = MutableStateFlow<Boolean?>(null) // null = no se ha intentado
    val isLoginSuccessful: StateFlow<Boolean?> = _isLoginSuccessful

    data class ValidationResult(val isValid: Boolean, val errorMessage: String)

    private var isBiometricAuthAvailable = false
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    private val _authenticationState = MutableStateFlow<Boolean>(false)
    val authenticationState: StateFlow<Boolean> = _authenticationState.asStateFlow()

    private val _passwordError = mutableStateOf<String?>("")
    val passwordError: State<String?> = _passwordError

    private val _emailError = mutableStateOf<String?>("")
    val emailError: State<String?> = _emailError

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
        var user: User? = null
        val validationEmail = validateEmail(email = email)
        val validationPassword = validatePassword(password = password)

        when {
            !validationEmail.isValid && !validationPassword.isValid -> {
                _emailError.value = validationEmail.errorMessage
                _passwordError.value = validationPassword.errorMessage
                _authenticationState.value = false
            }

            validationEmail.isValid && !validationPassword.isValid -> {
                _passwordError.value = validationPassword.errorMessage
                _authenticationState.value = false
                _emailError.value = null
            }

            !validationEmail.isValid && validationPassword.isValid -> {
                _emailError.value = validationEmail.errorMessage
                _authenticationState.value = false
                _passwordError.value = null
            }

            else -> {
                // Ambos son válidos
                _emailError.value = null
                _passwordError.value = null
                _authenticationState.value = true

                viewModelScope.launch {
                    _isLoading.value = true
                    _isLoginSuccessful.value = null
                    try {
                        val response =
                            client.get("https://6764320b52b2a7619f5bc6d6.mockapi.io/garmindata")
                        val body = response.bodyAsText()

                        println("--> ${response.request.method.value}  ${response.request.url} ")
                        println(body)
                        println("<-- END ${response.request.method.value}  ${response.request.url}")
                        println("<-- RESPONSE CODE ${response.status}")

                        val users: List<User> = Json.decodeFromString(body)

                        user = users.find { it.email == email && it.password == password }

                        if (user != null) {
                            _userState.value = user
                            _isLoginSuccessful.value = true
                            println("--> Login exitoso!")
                            println("Email: $email")
                            println("Password: $password")
                        } else {
                            _isLoginSuccessful.value = false
                            println("Login fallido: Usuario no encontrado o credenciales inválidas.")
                        }
                    } catch (e: Exception) {
                        _isLoginSuccessful.value = false // Maneja errores de red o de lógica
                    } finally {
                        _isLoading.value = false
                    }
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
//            email != Constants.DEFAULT_USERNAME -> {
//                _emailError.value = "Email does not match any account"
//                ValidationResult(false, "Invalid email")
//            }
            else -> {
                _emailError.value = null
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
//            password != Constants.DEFAULT_PASSWORD -> {
//                _passwordError.value = "Incorrect password"
//                ValidationResult(false, "Invalid password")
//            }
            else -> {
                _passwordError.value = null
                ValidationResult(true, "")
            }
        }
    }


}