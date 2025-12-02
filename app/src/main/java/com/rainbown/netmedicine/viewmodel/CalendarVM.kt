package com.rainbown.netmedicine.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rainbown.netmedicine.Dataa.TareaRepositoryImpl
import com.rainbown.netmedicine.Domainn.entity.Tarea
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class CalendarViewModel(private val context: Context) : ViewModel() {

    private val repository = TareaRepositoryImpl(context)

    private val _tareas = MutableStateFlow<List<Tarea>>(emptyList())
    val tareas: StateFlow<List<Tarea>> = _tareas

    private val _tareasFiltradas = MutableStateFlow<List<Tarea>>(emptyList())
    val tareasFiltradas: StateFlow<List<Tarea>> = _tareasFiltradas

    private val _selectedDate = MutableStateFlow<Long?>(null)
    val selectedDate: StateFlow<Long?> = _selectedDate


    fun loadTareas(idUsuario: Int) {
        viewModelScope.launch {
            repository.obtenerTareas(
                idUsuario = idUsuario,
                onSuccess = { lista ->
                    _tareas.value = lista
                    _tareasFiltradas.value = lista
                },
                onError = { error ->
                    println("Error al cargar tareas: $error")
                }
            )
        }
    }



    fun updateSelectedDate(dateMillis: Long) {
        _selectedDate.value = dateMillis

        val fechaCorta = formatDateToShort(dateMillis)

        val filtradas = _tareas.value.filter { tarea ->
            tarea.Fecha.trim() == fechaCorta.trim()
        }

        _tareasFiltradas.value = filtradas
        println("Fecha seleccionada: $fechaCorta — ${filtradas.size} tareas encontradas")
    }


    private fun formatDateToShort(millis: Long): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale("es", "ES"))
        return sdf.format(Date(millis))
    }
}
