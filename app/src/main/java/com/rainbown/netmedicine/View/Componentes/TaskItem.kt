package com.rainbown.netmedicine.View.Componentes

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Biotech
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Task
import androidx.compose.ui.graphics.vector.ImageVector

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rainbown.netmedicine.Domainn.entity.Tarea
import com.rainbown.netmedicine.Domainn.entity.TipoTarea
import com.rainbown.netmedicine.R
import com.rainbown.netmedicine.View.getTaskColor

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
                Text("Fecha: ${tarea.Fecha}", style = MaterialTheme.typography.bodySmall)
            }


        }
    }
}
