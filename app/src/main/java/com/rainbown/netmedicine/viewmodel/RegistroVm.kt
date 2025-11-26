package com.rainbown.netmedicine.viewmodel

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.MutableLiveData
import com.rainbown.netmedicine.Dataa.UserRepository
import com.rainbown.netmedicine.Domainn.usecase.RegistreeUsecase
import com.rainbown.netmedicine.domain.entity.UserEntity
import kotlinx.coroutines.launch

class RegistroVm(
    private val registreUsecase: RegistreeUsecase,
    private val userRepository: UserRepository

) : ViewModel() {

    val nombre = MutableLiveData<String>()
    val lastName = MutableLiveData<String>()
    val tel = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val genero = MutableLiveData<String>()
    val peso = MutableLiveData<String>()
    val altura = MutableLiveData<String>()
    val mensaje = MutableLiveData<String>()

    fun onRegistroClicked() {
        val nombreValue = nombre.value?.trim().orEmpty()
        val lastNameValue = lastName.value?.trim().orEmpty()
        val telValue = tel.value?.trim().orEmpty()
        val emailValue = email.value?.trim().orEmpty()
        val passwordValue = password.value?.trim().orEmpty()
        val generoValue = genero.value?.trim().orEmpty()
        val pesoValue = peso.value?.trim().orEmpty()
        val alturaValue = altura.value?.trim().orEmpty()

        if (nombreValue.isEmpty() || lastNameValue.isEmpty() || telValue.isEmpty() ||
            emailValue.isEmpty() || passwordValue.isEmpty() || generoValue.isEmpty() ||
            pesoValue.isEmpty() || alturaValue.isEmpty()) {
            mensaje.value = "Por favor completa todos los campos"
            return
        }

        viewModelScope.launch {
            try {
                val response = registreUsecase.invoke(
                    nombreValue,
                    lastNameValue,
                    emailValue,
                    telValue,
                    passwordValue,
                    generoValue,
                    pesoValue,
                    alturaValue
                )
                val user = UserEntity(
                    nombre = nombreValue,
                    apellido = lastNameValue,
                    correo = emailValue,
                    telefono = telValue,
                    genero = generoValue,
                    peso = pesoValue,
                    altura = alturaValue
                )
                userRepository.saveUser(user)

                mensaje.postValue("Registro completado correctamente")
                limpiarCampos()
                println("Registro completado correctamente: $response")

            } catch (e: Exception) {
                mensaje.postValue("Error en el registro: ${e.message}")
                println("Error en registro: ${e.message}")
                e.printStackTrace()
            }
        }
    }

    private fun limpiarCampos() {
        nombre.postValue("")
        lastName.postValue("")
        tel.postValue("")
        email.postValue("")
        password.postValue("")
        genero.postValue("")
        peso.postValue("")
        altura.postValue("")
    }
}
