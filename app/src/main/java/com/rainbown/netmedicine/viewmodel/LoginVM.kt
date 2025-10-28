package com.rainbown.netmedicine.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class LoginVM: ViewModel(){
val email = MutableLiveData<String>()
    val usr = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    fun onLoginChanged(emailValue: String, usrValue:String, passwordValue: String) {
        email.value = emailValue
        usr.value = usrValue
        password.value = passwordValue

    }
        fun onLoginSelected() {
        }
    }

