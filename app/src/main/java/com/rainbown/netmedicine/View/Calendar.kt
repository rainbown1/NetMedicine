package com.rainbown.netmedicine.View

import android.icu.text.SimpleDateFormat
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.rainbown.netmedicine.ui.theme.onPrimaryContainerLight
import com.rainbown.netmedicine.ui.theme.onPrimaryLight
import com.rainbown.netmedicine.ui.theme.outlineLight
import com.rainbown.netmedicine.ui.theme.primaryLight
import java.util.Date
import java.util.Locale


@RequiresApi(Build.VERSION_CODES.N)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Calendar(modifier: Modifier) {
    ConstraintLayout {
        var showCalendar by remember { mutableStateOf(false) }
        var selectedDate by remember { mutableStateOf("") }

        val datePickerState = rememberDatePickerState()

        val (boton,texto) = createRefs()
        Column(
            modifier = Modifier.padding(16.dp)
                .constrainAs(boton){
                    start.linkTo(parent.start)
                    top.linkTo(parent.top, margin = 30.dp)
                    end.linkTo(parent.end)
                },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = { showCalendar = true },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = primaryLight
                )
            ) {
                Text("Abrir Calendario", color = onPrimaryLight)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = selectedDate.ifEmpty { "Selecciona una fecha" },
                color = onPrimaryContainerLight,
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(16.dp))

            HorizontalDivider(thickness = 2.dp, color = outlineLight, modifier = Modifier
                .width(270.dp))

        }

        if (showCalendar) {
            DatePickerDialog(
                onDismissRequest = { showCalendar = false },
                confirmButton = {
                    Button(
                        onClick = {
                            datePickerState.selectedDateMillis?.let { millis ->
                                selectedDate = formatDateToCalendar(millis)
                            }
                            showCalendar = false
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = primaryLight
                        )
                    ) {
                        Text("Seleccionar")
                    }
                }
            ) {
                DatePicker(state = datePickerState)
                DatePicker(state = datePickerState)
            }
        }


    }
}

@RequiresApi(Build.VERSION_CODES.N)
fun formatDateToCalendar(millis: Long): String {
    val formatter = SimpleDateFormat("EEEE, d MMMM yyyy", Locale("es", "ES"))
    return formatter.format(Date(millis))
}