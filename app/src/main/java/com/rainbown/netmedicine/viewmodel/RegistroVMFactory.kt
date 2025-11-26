package com.rainbown.netmedicine.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rainbown.netmedicine.Dataa.UserRepository
import com.rainbown.netmedicine.Domainn.usecase.RegistreeUsecase


class RegistroVmFactory(
    private val registreUsecase: RegistreeUsecase,
    private val userRepository: UserRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegistroVm::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RegistroVm(registreUsecase,userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}