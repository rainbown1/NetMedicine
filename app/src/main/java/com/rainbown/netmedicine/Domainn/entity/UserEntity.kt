package com.rainbown.netmedicine.domain.entity


data class UserEntity(
    val id: Int ,
    val idPaciente: Int=0,
    val nombre: String,
    val apellido: String,
    val correo: String,
    val telefono: String,
    val contraseña: String? = null,
    val genero: String,
    val peso: String,
    val altura: String,
    val fotoPerfil: String? = null
)
