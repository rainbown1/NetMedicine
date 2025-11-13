package com.rainbown.netmedicine.Domainn.entity



data class Tarea(
    val idAgenda: Int,
    val Correo: String,
    val Titulo: String,
    val Descripcion: String,
    val Fecha: String,
    val tipo: TipoTarea,
    val completada: Boolean
)
enum class TipoTarea {
    EXAMEN,
    CONSULTA,
    MEDICAMENTO,
    RECORDATORIO
}
