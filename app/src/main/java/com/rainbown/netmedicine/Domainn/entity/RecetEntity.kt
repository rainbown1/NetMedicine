package com.rainbown.netmedicine.Domainn.entity

data class RecetEntity(
    val idReceta: Int,
    val correo: String,
    val medicamento: String,
    val cantidad: String,
    val admin: String,
    val periodo: String

)