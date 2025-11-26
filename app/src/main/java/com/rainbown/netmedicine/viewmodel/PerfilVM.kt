package com.rainbown.netmedicine.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rainbown.netmedicine.Dataa.UserRepository
import com.rainbown.netmedicine.domain.entity.UserEntity

// viewmodel/PerfilViewModel.kt
class PerfilViewModel(private val userRepository: UserRepository) : ViewModel() {

    val userData = MutableLiveData<UserEntity?>()
    val fotoPerfil = MutableLiveData<String?>()

    init {
        loadUserData()
    }

    fun loadUserData() {
        userData.value = userRepository.getUser()
        fotoPerfil.value = userRepository.getUser()?.fotoPerfil
    }

    fun updateFotoPerfil(path: String) {
        val currentUser = userRepository.getUser()
        currentUser?.let { user ->
            val updatedUser = user.copy(fotoPerfil = path)
            userRepository.saveUser(updatedUser)
            fotoPerfil.value = path
        }
    }

    fun updateUserData(updatedUser: UserEntity) {
        userRepository.saveUser(updatedUser)
        userData.value = updatedUser
    }
}