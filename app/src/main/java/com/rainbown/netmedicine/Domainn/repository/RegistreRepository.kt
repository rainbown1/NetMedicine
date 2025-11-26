package com.rainbown.netmedicine.Domainn.repository

import com.rainbown.netmedicine.domain.entity.UserEntity

interface RegistreRepository {
    suspend fun registre(nombre: String, apellido: String, correo: String, telefono: String, contraseña: String, genero: String, peso: String, altura: String): UserEntity?
}