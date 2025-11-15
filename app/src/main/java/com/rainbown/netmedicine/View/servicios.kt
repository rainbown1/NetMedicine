package com.rainbown.netmedicine.View

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.rainbown.netmedicine.View.Components.MyNavigationBar
import androidx.navigation.NavController
import com.rainbown.netmedicine.ui.theme.onPrimaryLight
import com.rainbown.netmedicine.ui.theme.onSecondaryLight
import com.rainbown.netmedicine.ui.theme.primaryLight
import com.rainbown.netmedicine.viewmodel.LoginVM

@Composable
fun pantallaservicios(navController: NavController){
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Servicios(navController = navController)
    }
}
@Composable
fun serviciosinfo(navController: NavController){
    Scaffold (modifier = Modifier.fillMaxSize()){ innerPadding ->
        Servicios(navController = navController)
    }
}
@Composable
fun Servicios(navController: NavController) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (header,content,menu) = createRefs()

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .background(primaryLight)
                .constrainAs(header) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Servicios Médicos",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = onSecondaryLight,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .constrainAs(content) {
                    top.linkTo(header.bottom, margin = 45.dp)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(servicios) { servicio ->
                    ServiceGridItem(servicio = servicio)
                }
            }
        }

        Box(modifier = Modifier.background(primaryLight).fillMaxWidth().constrainAs(menu){
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }){
           MyNavigationBar(navController)
        }//Box
    }
}

@Composable
fun ServiceGridItem(servicio: Servicio) {
    Card(
        onClick = {
            println("Servicio seleccionado: ${servicio.nombre}")
        },
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier
            .height(140.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(onSecondaryLight.copy(alpha = 0.1f), RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = servicio.icono,
                    contentDescription = servicio.nombre,
                    tint = primaryLight,
                    modifier = Modifier.size(28.dp)
                )
            }

            Text(
                text = servicio.nombre,
                fontSize = 17.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 8.dp)
            )

            Text(
                text = servicio.descripcion,
                fontSize = 13.sp,
                fontFamily = FontFamily.SansSerif,
                color = Color.DarkGray,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 4.dp),
                maxLines = 2
            )
        }

    }
}

val servicios = listOf(
    Servicio("S1","Consultas", Icons.Outlined.MedicalServices, "Atención médica"),
    Servicio("S2","Hospitales", Icons.Outlined.LocalHospital, "Ubicación"),
    Servicio("S3","Calendario", Icons.Outlined.CalendarMonth, "Citas Agendadas"),
    Servicio("S4","Especialidades", Icons.Outlined.MedicalInformation, "Cardiología, etc."),
    Servicio("S5","Laboratorio", Icons.Outlined.Science, "Análisis Clínicos"),
    Servicio("S6","Vacunación", Icons.Outlined.Vaccines, "Cartilla de Vacunación"),
    Servicio("S7","Farmacia", Icons.Outlined.Medication, "Medicamentos"),
    Servicio("S8","Rehabilitación", Icons.Outlined.Accessibility, "Terapias"),
    Servicio("S9","Nutrición", Icons.Outlined.Restaurant, "Asesoría"),
    Servicio("S10","Psicología", Icons.Outlined.Psychology, "Apoyo emocional"),
    Servicio("S11","", Icons.Outlined.Psychology, ""),
    Servicio("S12","", Icons.Outlined.Psychology, ""),

)

fun filtroServicio(servicioId: String): List<Servicio>{
    return servicios.filter { it.servicioId == servicioId }
}

data class Servicio(
    val servicioId: String,
    val nombre: String,
    val icono: androidx.compose.ui.graphics.vector.ImageVector,
    val descripcion: String
)