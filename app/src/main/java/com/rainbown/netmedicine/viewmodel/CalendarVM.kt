package com.rainbown.netmedicine.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rainbown.netmedicine.View.Tarea
import com.rainbown.netmedicine.View.TipoTarea
import com.rainbown.netmedicine.View.formatDateToShort
import com.rainbown.netmedicine.View.getCurrentDateFormatted
import com.rainbown.netmedicine.View.getTomorrowDateFormatted
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.N)
class CalendarViewModel : ViewModel() {
    private var _selectedDate = MutableStateFlow<Long?>(null)
    var selectedDate: StateFlow<Long?> = _selectedDate.asStateFlow()

    private var _tareas = MutableStateFlow<List<Tarea>>(emptyList())
    var tareas: StateFlow<List<Tarea>> = _tareas.asStateFlow()

    val tareasFiltradas: StateFlow<List<Tarea>> =
        selectedDate.combine(tareas) { date, tareasList ->
            date?.let {
                val fechaFiltro = formatDateToShort(it)
                tareasList.filter { tarea -> tarea.fecha == fechaFiltro }
            } ?: emptyList()
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    fun updateSelectedDate(dateMillis: Long?) {
        _selectedDate.value = dateMillis
    }

    fun loadTareas() {
        // Cargar tareas desde tu fuente de datos
        viewModelScope.launch {
            _tareas.value = listOf(
                Tarea(
                    id = 1,
                    titulo = "Tomar medicamento A",
                    descripcion = "1 tableta después del desayuno",
                    fecha = getCurrentDateFormatted(),
                    tipo = TipoTarea.MEDICAMENTO
                ),
                Tarea(
                    id = 1,
                    titulo = "Tomar medicamento A",
                    descripcion = "1 tableta después del desayuno",
                    fecha = getCurrentDateFormatted(),
                    tipo = TipoTarea.MEDICAMENTO
                ),
                Tarea(
                    id = 2,
                    titulo = "Consulta con cardiólogo",
                    descripcion = "Control mensual",
                    fecha = getCurrentDateFormatted(),
                    tipo = TipoTarea.CONSULTA
                ),
                Tarea(
                    id = 3,
                    titulo = "Examen de sangre",
                    descripcion = "Ayuno 12 hrs",
                    fecha = getTomorrowDateFormatted(),
                    tipo = TipoTarea.EXAMEN
                )
            )
        }
    }
}
