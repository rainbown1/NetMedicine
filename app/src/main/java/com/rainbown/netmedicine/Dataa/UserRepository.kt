package com.rainbown.netmedicine.Dataa

import android.content.SharedPreferences
import com.rainbown.netmedicine.domain.entity.UserEntity

// Data/repository/UserRepository.kt
class UserRepository(private val prefs: SharedPreferences) {

    private companion object {
        const val PREFS_USER_DATA = "user_data"
        const val KEY_NOMBRE = "nombre"
        const val KEY_APELLIDO = "apellido"
        const val KEY_CORREO = "correo"
        const val KEY_TELEFONO = "telefono"
        const val KEY_GENERO = "genero"
        const val KEY_PESO = "peso"
        const val KEY_ALTURA = "altura"
        const val KEY_FOTO_PERFIL = "foto_perfil"
    }

    // Método para guardar usuario - DEBE ser público
    fun saveUser(user: UserEntity) {
        prefs.edit().apply {
            putString(KEY_NOMBRE, user.nombre)
            putString(KEY_APELLIDO, user.apellido)
            putString(KEY_CORREO, user.correo)
            putString(KEY_TELEFONO, user.telefono)
            putString(KEY_GENERO, user.genero)
            putString(KEY_PESO, user.peso)
            putString(KEY_ALTURA, user.altura)
            user.fotoPerfil?.let { putString(KEY_FOTO_PERFIL, it) }
            apply()
        }
    }

    // Método para obtener usuario
    fun getUser(): UserEntity? {
        val nombre = prefs.getString(KEY_NOMBRE, null) ?: return null
        return UserEntity(
            nombre = nombre,
            apellido = prefs.getString(KEY_APELLIDO, "") ?: "",
            correo = prefs.getString(KEY_CORREO, "") ?: "",
            telefono = prefs.getString(KEY_TELEFONO, "") ?: "",
            genero = prefs.getString(KEY_GENERO, "") ?: "",
            peso = prefs.getString(KEY_PESO, "") ?: "",
            altura = prefs.getString(KEY_ALTURA, "") ?: "",
            fotoPerfil = prefs.getString(KEY_FOTO_PERFIL, null)
        )
    }

    fun isUserLoggedIn(): Boolean = getUser() != null
}