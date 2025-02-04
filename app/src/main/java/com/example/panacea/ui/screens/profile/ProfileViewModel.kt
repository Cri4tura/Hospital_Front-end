package com.example.panacea.ui.screens.profile

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.panacea.domain.models.nurse.Nurse
import com.example.panacea.data.repositories.NurseRepositoryImpl
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

class ProfileViewModel(
    private val repository: NurseRepositoryImpl
) : ViewModel() {

    var state by mutableStateOf(UiState())
        private set

    var data by mutableStateOf(UiData())
        private set

    init {
        viewModelScope.launch {
            state = UiState(isLoading = true)
            data = UiData(repository.getCurrentNurse())
            state = UiState(isLoading = false)
        }
    }

    fun deleteAccount(userId: Int) {
        viewModelScope.launch {
            state = UiState(isLoading = true)
            repository.deleteNurse(userId).collect {
                state = state.copy(isLoading = false, isDeleted = true)
            }
        }
    }

    fun updateNurse(updateData: Nurse) {
        viewModelScope.launch {
            state = UiState(isLoading = true)
            repository.updateNurse(updateData).collect{
                data = UiData(updateData)
                state = state.copy(isLoading = false, isUpdated = true)
            }
        }
    }

    fun changeImage() {
        data.currentUser?.profileImage = "fake_profile_img"
    }

    fun saveImageToInternalStorage(context: Context, uri: Uri): String? {
        return try {
            val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
            val file = File(context.filesDir, "saved_image.jpg") // Nombre del archivo guardado
            val outputStream = FileOutputStream(file)

            inputStream?.copyTo(outputStream)

            inputStream?.close()
            outputStream.close()

            Log.d("TAG", "Ruta del archivo guardado: ${file.absolutePath}")

            // Actualizar el objeto currentUser con la nueva imagen
            data.currentUser?.let { currentUser ->
                val updatedUser = currentUser.copy(profileImage = file.absolutePath)

                // Actualizamos el estado local
                data = UiData(updatedUser)

                // Actualizamos la base de datos en una corrutina
                viewModelScope.launch {
                    repository.updateNurse(updatedUser).collect {
                        state = state.copy(isUpdated = true)
                        Log.d("TAG", "Imagen de perfil actualizada en la base de datos")
                    }
                }
            }

            file.absolutePath  // Retornamos la ruta del archivo guardado

        } catch (e: Exception) {
            e.printStackTrace()
            state = state.copy(onError = true)
            null
        }
    }

    data class UiState(
        val isLoading: Boolean = false,
        val isDeleted: Boolean = false,
        val isUpdated: Boolean = false,
        val onError: Boolean = false,
    )

    data class UiData(
        val currentUser: Nurse? = null
    )
}