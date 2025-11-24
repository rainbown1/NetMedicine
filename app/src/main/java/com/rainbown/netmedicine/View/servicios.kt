package com.rainbown.netmedicine.View

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.rainbown.netmedicine.View.Components.barra
import com.rainbown.netmedicine.ui.theme.onSecondaryLight
import com.rainbown.netmedicine.ui.theme.primaryLight

@Composable
fun pantallaservicios(navController: NavController){
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Servicios(navController = navController)
    }
}

@Composable
fun Servicios(navController: NavController) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (header,content,menu) = createRefs()

        Box(
            modifier = Modifier
                .constrainAs(header) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {
            barra("Servicios Medicos")
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
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(servicios){text ->
                        ServiceGridItem(servicio = text,navController = navController)
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
fun ServiceGridItem(servicio: Servicio, navController: NavController) {
    Card(
        onClick = {
            navController.navigate("servicios/${servicio.servicioId}")
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
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

    }
}

val servicios = listOf(
    Servicio("S1","Otorrinolaringologia", Icons.Outlined.Medication),
    Servicio("S2","Urología", Icons.Outlined.LocalHospital),
    Servicio("S3","CIRUGÍA PLÁSTICA Y RECONSTRUCTIVA", Icons.Outlined.Biotech),
    Servicio("S4","ONCOLOGÍA", Icons.Outlined.MedicalInformation),
    Servicio("S5","Laboratorio Central", Icons.Outlined.Biotech),
    Servicio("S6","Manual de Organización", Icons.Outlined.Book),
    Servicio("S7","Consulta Externa", Icons.Outlined.Medication),
    Servicio("S8","Infectología", Icons.Outlined.Vaccines),
    Servicio("S9","OFTALMOLOGIA", Icons.Outlined.RemoveRedEye),
    Servicio("S10","", Icons.Outlined.Psychology),
)

fun filtroServicios(serviciosId: String?): List<ServicioInfo>{
    return infoservicios.filter { it.servicioId == serviciosId }
}

data class Servicio(
    val servicioId: String,
    val nombre: String,
    val icono: androidx.compose.ui.graphics.vector.ImageVector
)