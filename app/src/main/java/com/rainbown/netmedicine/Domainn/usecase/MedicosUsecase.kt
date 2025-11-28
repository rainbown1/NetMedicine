package com.rainbown.netmedicine.Domainn.usecase

import com.rainbown.netmedicine.Domainn.entity.MedicoEntity
import com.rainbown.netmedicine.Domainn.repository.MedicosRepository

class ObtenerMedicosUseCase(private val repo: MedicosRepository) {
    operator fun invoke(
        callback: (List<MedicoEntity>) -> Unit,
        error: (String) -> Unit
    ) {
        repo.obtenerMedicos(callback, error)
    }
}
