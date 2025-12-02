package com.rainbown.netmedicine.Domainn.repository

import com.rainbown.netmedicine.Domainn.entity.Tarea

interface AgendRepository {
  fun obtenerTareas(
        idUsuario: Int,
        onSuccess: (List<Tarea>) -> Unit,
        onError: (String) -> Unit
    )

}
