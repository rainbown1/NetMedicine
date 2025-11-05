package com.rainbown.netmedicine.View

import android.icu.text.SimpleDateFormat
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.rainbown.netmedicine.ui.theme.outlineLight
import java.util.Date
import java.util.Locale

// Modelo de datos para las tareas
data class Tarea(
    val id: Int,
    val titulo: String,
    val descripcion: String,
    val fecha: String,
    val tipo: TipoTarea,
    val completada: Boolean = false
)

enum class TipoTarea {
    MEDICAMENTO,
    CONSULTA,
    EXAMEN,
    RECORDATORIO
}

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun CalendarWithTasks(modifier: Modifier = Modifier) {

    var showCalendar by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf("") }
    var selectedDateMillis by remember { mutableStateOf<Long?>(null) }

    val datePickerState = rememberDatePickerState()

    val tareas = remember {
        listOf(
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

    val tareasFiltradas = if (selectedDate.isNotEmpty() && selectedDateMillis != null) {
        val fechaFiltro = formatDateToShort(selectedDateMillis!!)
        tareas.filter { it.fecha == fechaFiltro }
    } else emptyList()

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = { showCalendar = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Abrir Calendario")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = selectedDate.ifEmpty { "Selecciona una fecha" },
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center
            )
        }

        TaskBar(
            tareas = tareasFiltradas,
            fechaSeleccionada = selectedDate,
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.6f)
        )
    }

    if (showCalendar) {
        DatePickerDialog(
            onDismissRequest = { showCalendar = false },
            confirmButton = {
                Button(
                    onClick = {
                    datePickerState.selectedDateMillis?.let {
                        selectedDateMillis = it
                        selectedDate = formatDateToCalendar(it)
                    }
                    showCalendar = false
                }) {
                    Text("Seleccionar")
                }
            },
            dismissButton = {
                Button(onClick = { showCalendar = false }) {
                    Text("Cancelar")
                }
            }
        ) {
            DatePicker(state = datePickerState)
            HorizontalDivider(thickness = 2.dp, color = outlineLight, modifier = Modifier
            .width(270.dp))
        }

    }
}



@Composable
fun TaskBar(
    tareas: List<Tarea>,
    fechaSeleccionada: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.padding(16.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = if (fechaSeleccionada.isNotEmpty()) "Tareas del día" else "Tareas",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (tareas.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No hay tareas para esta fecha")
                }
            } else {
                LazyColumn {
                    items(tareas) { tarea ->
                        TaskItem(tarea = tarea)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun TaskItem(tarea: Tarea) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = getTaskColor(tarea.tipo).copy(alpha = 0.1f)
        )
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(getTaskColor(tarea.tipo), RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = when (tarea.tipo) {
                        TipoTarea.MEDICAMENTO, TipoTarea.CONSULTA -> Icons.Default.MedicalServices
                        else -> Icons.Default.Warning
                    },
                    contentDescription = null,
                    tint = Color.White
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(Modifier.weight(1f)) {
                Text(tarea.titulo, fontWeight = FontWeight.Bold)
                Text(tarea.descripcion, maxLines = 2)
            }

            Box(
                modifier = Modifier
                    .size(12.dp)
                    .background(if (tarea.completada) Color.Green else Color.Red, CircleShape)
            )
        }
    }
}

// Colores
fun getTaskColor(tipo: TipoTarea): Color = when (tipo) {
    TipoTarea.MEDICAMENTO -> Color(0xFF4CAF50)
    TipoTarea.CONSULTA -> Color(0xFF2196F3)
    TipoTarea.EXAMEN -> Color(0xFFFF9800)
    TipoTarea.RECORDATORIO -> Color(0xFF9C27B0)
}

// Formatos de fecha
@RequiresApi(Build.VERSION_CODES.N)
fun formatDateToCalendar(millis: Long): String =
    SimpleDateFormat("EEEE, d MMMM yyyy", Locale("es", "ES")).format(Date(millis))

@RequiresApi(Build.VERSION_CODES.N)
fun formatDateToShort(millis: Long): String =
    SimpleDateFormat("dd/MM/yyyy", Locale("es", "ES")).format(Date(millis))

@RequiresApi(Build.VERSION_CODES.N)
fun getCurrentDateFormatted(): String =
    SimpleDateFormat("dd/MM/yyyy", Locale("es", "ES")).format(Date())

@RequiresApi(Build.VERSION_CODES.N)
fun getTomorrowDateFormatted(): String {
    val calendar = java.util.Calendar.getInstance()
    calendar.add(java.util.Calendar.DAY_OF_YEAR, 1)
    return SimpleDateFormat("dd/MM/yyyy", Locale("es", "ES")).format(calendar.time)
}
