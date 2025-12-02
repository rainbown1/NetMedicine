package com.rainbown.netmedicine.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.rainbown.netmedicine.domain.entity.UserEntity
import com.rainbown.netmedicine.domain.usecase.LoginUsecase
import kotlinx.coroutines.launch

class LoginVM(
    private val loginUseCase: LoginUsecase,
    private val context: Context
) : ViewModel() {

    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val usuarioLiveData = MutableLiveData<UserEntity?>()
    val errorLiveData = MutableLiveData<String?>()

    fun onLoginSelected() {
        val correo = email.value.orEmpty()
        val pass = password.value.orEmpty()

        if (correo.isBlank() || pass.isBlank()) {
            errorLiveData.value = "Por favor, completa todos los campos"
            return
        }

        viewModelScope.launch {
            try {
                val user = loginUseCase(correo, pass)

                if (user != null) {
                    guardarDatosLocal(user)
                    usuarioLiveData.postValue(user)
                }

            } catch (e: Exception) {
                val message = e.message ?: "Ocurrió un error inesperado"
                errorLiveData.postValue(message)
            }
        }
    }

    private fun guardarDatosLocal(user: UserEntity) {

        val prefs = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)

        prefs.edit()
            .putInt("id_usuario", user.id)
            .putInt("id_paciente", user.idPaciente)
            .putString("correo", user.correo)
            .putString("nombre", user.nombre)
            .apply()

        println("LOGIN OK → id_usuario=${user.id}, id_paciente=${user.idPaciente}")
    }

    fun clearError() {
        errorLiveData.value = null
    }

    class LoginVMFactory(
        private val loginUseCase: LoginUsecase,
        private val context: Context
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LoginVM::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return LoginVM(loginUseCase, context) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
