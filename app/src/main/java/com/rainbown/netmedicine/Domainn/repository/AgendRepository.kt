package com.rainbown.netmedicine.Domainn.repository

import com.rainbown.netmedicine.Domainn.entity.Tarea

interface AgendRepository {
    fun obtenerTareas(
        correo: String,
        onSuccess: (List<Tarea>) -> Unit,
        onError: (String) -> Unit
    )
}
