package com.example.panacea.ui.screens.login

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.util.Patterns
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.panacea.data.repositories.NurseRepositoryImpl
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.HttpRetryException

class LoginViewModel(
    private val repository: NurseRepositoryImpl
) : ViewModel() {

    private var isBiometricAuthAvailable = false
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    var state by mutableStateOf(UiState())
        private set

    private val _passwordError = mutableStateOf("")
    val passwordError: State<String> = _passwordError
    private val _emailError = mutableStateOf("")
    val emailError: State<String> = _emailError

    fun setupAuth(context: Context) {

        if (isBiometricAuthAvailable) return  // Evitar configuración redundante

        val biometricManager = BiometricManager.from(context)
        val canAuthenticate = biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL)

        if (canAuthenticate == BiometricManager.BIOMETRIC_SUCCESS) {
            isBiometricAuthAvailable = true
            promptInfo = BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric Authentication")
                .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL)
                .build()
        }
    }

    // Llama a setupAuth cuando el usuario intente iniciar sesión o acceder a una pantalla protegida
    fun onLoginClicked(context: Context) {
        setupAuth(context)
        // Llamar luego al método de autenticación, si la biometría está disponible
        if (isBiometricAuthAvailable) {
            authenticate(context as FragmentActivity) { success ->
                if (success) {
                    // Autenticación exitosa
                    Log.d(TAG, "BIOMETRICS LOGIN____!")
                } else {
                    // Autenticación fallida
                    Log.e(TAG, "BIOMETRICS LOGIN____ERROR!")
                }
            }
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
                    state = UiState(isLogged = true, isLoading = false)
                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    onAuthenticationResult(false)
                    state = UiState(isLoading = false, onError = true)
                }
            }

            BiometricPrompt(
                context,
                ContextCompat.getMainExecutor(context),
                authenticationCallback
            ).authenticate(promptInfo)

        } else {
            onAuthenticationResult(true)
            state = UiState(isLogged = true, isLoading = false)
        }
    }

    fun login(email: String, password: String) {

        val validationEmail = validateEmail(email)
        val validationPassword = validatePassword(password)

        when {
            !validationEmail.isValid && !validationPassword.isValid -> {
                _emailError.value = validationEmail.errorMessage
                _passwordError.value = validationPassword.errorMessage
            }

            validationEmail.isValid && !validationPassword.isValid -> {
                _passwordError.value = validationPassword.errorMessage
            }

            !validationEmail.isValid && validationPassword.isValid -> {
                _emailError.value = validationEmail.errorMessage
            }

            else -> {
                viewModelScope.launch {
                    state = UiState(isLoading = true)
                    try {
                        repository.login(email, password).collect { nurse ->
                            if (nurse != null) {
                                state = UiState(
                                    log = "LOGIN SUCCESS!",
                                    isLoading = false,
                                    isLogged = true,
                                    onSuccess = true,
                                )
                                _emailError.value = ""
                                _passwordError.value = ""

                            } else {
                                state = UiState(
                                    log = "LOGIN NOT SUCCESS!",
                                    isLoading = false,
                                    isLogged = false,
                                    onError = false
                                )
                                _emailError.value = " "
                                _passwordError.value = "Incorrect email or password"
                            }
                        }
                    } catch (e: HttpRetryException) {
                        Log.e(TAG, "Error de red: ${e.localizedMessage}")
//                        state = UiState(onError = true, isLoading = false)
                        _emailError.value = "Error de conexión. Inténtalo de nuevo."
                    } catch (e: Exception) {
                        Log.e(TAG, "Error msg: ${e.localizedMessage}")
                        state = UiState(onError = true, isLoading = false)
                        _emailError.value = " "
                        _passwordError.value = e.localizedMessage
                    } finally {
                        Log.i(TAG,"FINAL STATE: $state\n" +
                                "LOGIN ENDED.....")
                    }
                }
            }
        }
    }


    private fun validateEmail(email: String): UiValid {
        return when {
            email.isBlank() -> {
                _emailError.value = "Email cannot be empty"
                UiValid(false, "Email is empty")
            }

            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                _emailError.value = "Invalid email format"
                UiValid(false, "Invalid email format")
            }

            else -> {
                _emailError.value = ""
                UiValid(true, "")
            }
        }
    }

    private fun validatePassword(password: String): UiValid {
        return when {
            password.isBlank() -> {
                _passwordError.value = "Password cannot be empty"
                UiValid(false, "Password is empty")
            }

            password.length < 4 -> {
                _passwordError.value = "Password must be at least 4 characters long"
                UiValid(false, "Password too short")
            }

            else -> {
                _passwordError.value = ""
                UiValid(true, "")
            }
        }
    }

    data class UiState(
        val log: String = " ",
        val isLoading: Boolean = false,
        val isLogged: Boolean = false,
        val onError: Boolean = false,
        val onSuccess: Boolean = false
    )

    data class UiValid(
        val isValid: Boolean,
        val errorMessage: String
    )
}