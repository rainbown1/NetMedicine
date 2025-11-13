package com.rainbown.netmedicine.Domainn.usecase

import com.rainbown.netmedicine.Domainn.entity.Tarea
import com.rainbown.netmedicine.Domainn.repository.AgendRepository

class AgendUsecase(private val repository: AgendRepository) {
    fun execute(
        correo: String,
        onSuccess: (List<Tarea>) -> Unit,
        onError: (String) -> Unit
    ) {
        repository.obtenerTareas(correo, onSuccess, onError)
    }
}
