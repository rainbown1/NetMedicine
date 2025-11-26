package com.rainbown.netmedicine.Domainn.usecase

import com.rainbown.netmedicine.Domainn.repository.RegistreRepository
import com.rainbown.netmedicine.domain.entity.UserEntity

class RegistreeUsecase(
    private val repository: RegistreRepository
) {
    suspend operator fun invoke(
        nombre: String,
        apellido: String,
        correo: String,
        telefono: String,
        contraseña: String,
        genero: String,
        peso: String,
        altura: String
    ): UserEntity? {
        println("UseCase: ejecutando registro con $nombre $apellido")
        return repository.registre(nombre, apellido, correo, telefono, contraseña, genero, peso, altura)
    }
}
