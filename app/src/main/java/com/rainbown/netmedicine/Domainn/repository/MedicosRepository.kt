package com.rainbown.netmedicine.Domainn.repository

import com.rainbown.netmedicine.Domainn.entity.MedicoEntity

interface MedicosRepository {
    fun obtenerMedicos(callback: (List<MedicoEntity>) -> Unit, error: (String) -> Unit)
}
