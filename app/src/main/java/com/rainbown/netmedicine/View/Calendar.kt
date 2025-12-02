package com.rainbown.netmedicine.View

import android.content.Context
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.rainbown.netmedicine.Domainn.entity.Tarea
import com.rainbown.netmedicine.Domainn.entity.TipoTarea
import com.rainbown.netmedicine.View.Components.MyNavigationBar
import com.rainbown.netmedicine.View.Components.barra
import com.rainbown.netmedicine.ui.theme.onPrimaryContainerLight
import com.rainbown.netmedicine.ui.theme.outlineLight
import com.rainbown.netmedicine.ui.theme.primaryLight
import com.rainbown.netmedicine.viewmodel.CalendarViewModel
import com.rainbown.netmedicine.viewmodel.CalendarVMFactory
import java.util.*

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun pantallaagendas(navController: NavController) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        CalendarWithTasks(
            modifier = Modifier.padding(innerPadding),
            navController = navController
        )
    }
}

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun CalendarWithTasks(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val context = LocalContext.current
    val viewModel: CalendarViewModel = viewModel(factory = CalendarVMFactory(context))

    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (barra, tasksB, menu, boton) = createRefs()
        var showCalendar by remember { mutableStateOf(false) }

        val selectedDateMillis by viewModel.selectedDate.collectAsState()
        val tareasFiltradas by viewModel.tareasFiltradas.collectAsState()

        val selectedDate = remember(selectedDateMillis) {
            selectedDateMillis?.let { formatDateToCalendar(it) } ?: ""
        }


        LaunchedEffect(Unit) {
            val sharedPref = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
            val idUsuario = sharedPref.getInt("id_usuario", -1)

            if (idUsuario != -1) {
                println("Cargando tareas para usuario ID = $idUsuario")
                viewModel.loadTareas(idUsuario)
            } else {
                println("No se encontró id_usuario guardado")
            }
        }

        val datePickerState = rememberDatePickerState()


        Box(
            modifier = Modifier.constrainAs(barra) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                end.linkTo(parent.end)
            }
        ) {
            barra("Agenda")
        }


        Column(
            modifier = modifier
                .fillMaxSize()
                .constrainAs(boton) {
                    start.linkTo(parent.start)
                    top.linkTo(barra.bottom, margin = 15.dp)
                    end.linkTo(parent.end)
                }
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = { showCalendar = true },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = onPrimaryContainerLight,
                        contentColor = Color.White
                    ),
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
        }


        Box(
            modifier = Modifier
                .background(primaryLight)
                .fillMaxWidth()
                .constrainAs(menu) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {
            MyNavigationBar(navController)
        }


        Box(modifier = Modifier.constrainAs(tasksB) {
            start.linkTo(parent.start)
            bottom.linkTo(menu.top)
            end.linkTo(parent.end)
        }) {
            TaskBar(
                tareas = tareasFiltradas,
                fechaSeleccionada = selectedDate,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
            )
        }


        if (showCalendar) {
            DatePickerDialog(
                onDismissRequest = { showCalendar = false },
                confirmButton = {
                    Button(
                        onClick = {
                            datePickerState.selectedDateMillis?.let { dateMillis ->
                                viewModel.updateSelectedDate(dateMillis)
                            }
                            showCalendar = false
                        }
                    ) {
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
                HorizontalDivider(
                    thickness = 2.dp,
                    color = outlineLight,
                    modifier = Modifier.width(270.dp)
                )
            }
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
                Text(tarea.Titulo, fontWeight = FontWeight.Bold)
                Text(tarea.Descripcion, maxLines = 2)
            }

            Box(
                modifier = Modifier
                    .size(12.dp)
                    .clip(CircleShape)
                    .background(if (tarea.completada) Color.Green else Color.Red)
            )
        }
    }
}

fun getTaskColor(tipo: TipoTarea): Color = when (tipo) {
    TipoTarea.MEDICAMENTO -> Color(0xFF4CAF50)
    TipoTarea.CONSULTA -> Color(0xFF2196F3)
    TipoTarea.EXAMEN -> Color(0xFFFF9800)
    TipoTarea.RECORDATORIO -> Color(0xFF9C27B0)
}

@RequiresApi(Build.VERSION_CODES.N)
fun formatDateToCalendar(millis: Long): String =
    SimpleDateFormat("EEEE, d MMMM yyyy", Locale("es", "ES")).format(Date(millis))

@RequiresApi(Build.VERSION_CODES.N)
fun formatDateToShort(millis: Long): String =
    SimpleDateFormat("dd/MM/yyyy", Locale("es", "ES")).format(Date(millis))
