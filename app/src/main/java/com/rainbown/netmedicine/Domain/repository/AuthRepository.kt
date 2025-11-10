package com.rainbown.netmedicine.domain.repository

import com.rainbown.netmedicine.domain.entity.UserEntity

interface AuthRepository {
    suspend fun login(correo: String, contraseña: String): UserEntity?
}
