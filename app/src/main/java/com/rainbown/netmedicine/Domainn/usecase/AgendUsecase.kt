package com.rainbown.netmedicine.Domainn.usecase

import com.rainbown.netmedicine.Domainn.entity.Tarea
import com.rainbown.netmedicine.Domainn.repository.AgendRepository

class AgendUsecase(private val repository: AgendRepository) {

    fun execute(
        idUsuario: Int,
        onSuccess: (List<Tarea>) -> Unit,
        onError: (String) -> Unit
    ) {
        repository.obtenerTareas(idUsuario, onSuccess, onError)
    }
}

