package com.rainbown.netmedicine.domain.entity

data class UserEntity(
    val id: Int,
    val nombre: String,
    val apellido: String,
    val correo: String,
    val telefono: String
)
