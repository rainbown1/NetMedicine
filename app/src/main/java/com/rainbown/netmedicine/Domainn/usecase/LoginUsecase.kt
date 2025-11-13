package com.rainbown.netmedicine.domain.usecase

import com.rainbown.netmedicine.domain.entity.UserEntity
import com.rainbown.netmedicine.domain.repository.AuthRepository

class LoginUsecase(private val repository: AuthRepository) {
    suspend operator fun invoke(correo: String, contraseña: String): UserEntity? {
        return repository.login(correo, contraseña)
    }
}
