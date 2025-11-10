package com.rainbown.netmedicine.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rainbown.netmedicine.domain.entity.UserEntity
import com.rainbown.netmedicine.domain.usecase.Login
import kotlinx.coroutines.launch

class LoginVM(
    private val loginUseCase: Login
) : ViewModel() {

    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val usuarioLiveData = MutableLiveData<UserEntity?>()
    val errorLiveData = MutableLiveData<String?>()

    fun onLoginSelected() {
        viewModelScope.launch {
            try {
                val user = loginUseCase(email.value ?: "", password.value ?: "")
                if (user != null) usuarioLiveData.postValue(user)
                else errorLiveData.postValue("Correo o contraseña incorrectos")
            } catch (e: Exception) {
                errorLiveData.postValue("Error: ${e.message}")
            }
        }
    }
}
