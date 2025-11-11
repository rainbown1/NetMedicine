package com.rainbown.netmedicine.View

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.outlined.Markunread
import androidx.compose.material.icons.outlined.Medication
import androidx.compose.material.icons.outlined.ViewAgenda
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.rainbown.netmedicine.R
import com.rainbown.netmedicine.View.Components.MyNavigationBar
import com.rainbown.netmedicine.View.Components.barra
import com.rainbown.netmedicine.ui.theme.onSecondaryLight

@Composable
fun PantallaMedicosPorHospital(navController: NavController, hospitalId: String?) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Medicos(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            hospitalId = hospitalId
        )
    }
}



@Composable
fun Medicos(modifier: Modifier, navController: NavController, hospitalId: String?){
    ConstraintLayout {
        val (barra,contenedor,menu)= createRefs()

        val medicosFiltrados = remember(hospitalId) {
            if (hospitalId != null) {
                filtroMedicos(hospitalId)
            } else {
                emptyList()
            }
        }

        val nombreHospital = remember(hospitalId) {
            hospitales.find { it.id == hospitalId }?.nombre ?: "Hospital"
        }

        Box(modifier = Modifier.constrainAs(barra){
            start.linkTo(parent.start)
            top.linkTo(parent.top)
            end.linkTo(parent.end)
        }){
            barra("Medicos -> $nombreHospital")
        }

        Box(modifier = Modifier.constrainAs(contenedor){
            start.linkTo(parent.start)
            top.linkTo(barra.bottom, margin = 15.dp)
            end.linkTo(parent.end)
        }){
            if (medicosFiltrados.isEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "No hay médicos disponibles para este hospital",
                        fontSize = 16.sp,
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                LazyColumn (
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(15.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ){
                    items(medicosFiltrados){medico ->
                        MedicosCards(medico = medico)
                    }
                }
            }
        }


        Box(modifier = Modifier.constrainAs(menu){
            start.linkTo(parent.start)
            bottom.linkTo(parent.bottom)
            end.linkTo(parent.end)
        }){
            MyNavigationBar(navController)
        }

    }
}

val medicos = listOf(
            Medico("DR. J. Lauriel Arzac Ramirez","Medico General","Hospital 5"),
            Medico("DR. 1", "Especialidad 1", "H1"),
            Medico("DR. 2", "Especialidad 1", "H1"),
            Medico("DR. 3", "Especialidad 1", "H2"),
            Medico("DR. 4", "Especialidad 1", "H2"),
            Medico("DR. 5", "Especialidad 1", "H3"),
            Medico("DR. 6", "Especialidad 1", "H3"),
            Medico("DR. 7", "Especialidad 1", "H4"),
            Medico("DR. 8", "Especialidad 1", "H4"),
            Medico("DR. 9", "Especialidad 1", "H5")
)

@Composable
fun MedicosCards(medico: Medico){
    Card(
        onClick = {
            println("Medico: ${medico.nombre}")
        },
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier
            .height(140.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            contentColor = Color.White,
            containerColor = onSecondaryLight
        )
    ) {
        Row (
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(onSecondaryLight.copy(alpha = 0.1f), RoundedCornerShape(10.dp)),
            ) {
               Image(
                   painter = painterResource(R.drawable.medico),
                   contentDescription = "Foto medico",
                   modifier = Modifier
               )
            }

            Column {
                Text(
                    text = medico.nombre,
                    fontSize = 17.sp,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 8.dp)
                )

                Text(
                    text = medico.especialidad,
                    fontSize = 13.sp,
                    fontFamily = FontFamily.SansSerif,
                    color = Color.DarkGray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 4.dp),
                    maxLines = 2
                )
                OutlinedButton(
                    onClick = { }
                ) {
                    Row {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = "Ubicacion de hospital"
                        )
                        Text(
                            text = medico.idHospital,
                            fontSize = 13.sp,
                            fontFamily = FontFamily.SansSerif,
                            color = Color.DarkGray,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(top = 4.dp),
                            maxLines = 2
                        )
                    } //Row
                }
            }
        }

    }
}

data class Medico(
    val nombre: String,
    val especialidad: String,
    val idHospital: String
)