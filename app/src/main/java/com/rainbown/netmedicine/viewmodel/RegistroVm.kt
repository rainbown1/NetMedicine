package com.rainbown.netmedicine.viewmodel

import androidx.compose.runtime.MutableState
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RegistroVm: ViewModel() {
    val nombre = MutableLiveData<String>()
    val lastName = MutableLiveData<String>()
    val tel = MutableLiveData<String>()
    val email = MutableLiveData<String>()

    val password = MutableLiveData<String>()

    fun onregistroChanged(nombreValue:String,lastnameValue:String,telValue:String,emailValue: String, passwordValue: String) {
        nombre.value = nombreValue
        lastName.value = lastnameValue
        tel.value = telValue
        email.value = emailValue
        password.value = passwordValue

    }
    fun onregistroSelected() {
    }
}