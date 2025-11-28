package com.rainbown.netmedicine.Domainn.entity


data class MedicoEntity(
    val idMedico: Int,
    val nombre: String,
    val especialidad: String,
    val idHospital: String,
    val contacto: String
)
