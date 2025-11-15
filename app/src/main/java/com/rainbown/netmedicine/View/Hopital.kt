package com.rainbown.netmedicine.View

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.rainbown.netmedicine.navegacion.ScreenNav
import com.rainbown.netmedicine.ui.theme.onSecondaryLight

@Composable
fun pantallahospitales(navController: NavController){
    Scaffold (modifier = Modifier.fillMaxSize()){ innerPadding ->
        Hospitales(modifier = Modifier.padding(innerPadding), navController = navController)
    }
}

@Composable
fun Hospitales(modifier: Modifier, navController: NavController){
    ConstraintLayout {
        val (barra,contenedor,menu)= createRefs()

        Box(modifier = Modifier.constrainAs(barra){
            start.linkTo(parent.start)
            top.linkTo(parent.top)
            end.linkTo(parent.end)
        }){
            barra("Hospitales")
        }

        Box(modifier = Modifier.constrainAs(contenedor){
            start.linkTo(parent.start)
            top.linkTo(barra.bottom, margin = 15.dp)
            end.linkTo(parent.end)
        }){
            LazyColumn (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(15.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ){
                items(hospitales) {hospital->
                    HospitalesCards(hospital = hospital, navController)
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

val hospitales = listOf(
    Hospital("H1","Hospital 1","Hospital privado","Av. falsa calle falsa #1234"),
    Hospital("H2","Hospital 2","Hospital general","Av. falsa calle falsa #1234"),
    Hospital("H3","Hospital 3","Hospital privado","Av. falsa calle falsa #1234"),
    Hospital("H4","Hospital 4","Hospital general","Av. falsa calle falsa #1234"),
    Hospital("H5","Hospital 5","Hospital general","Av. falsa calle falsa #1234")
    )

@Composable
fun HospitalesCards(hospital: Hospital, navController: NavController){
    Card(
        onClick = {
            navController.navigate("medicos/${hospital.id}")
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
                    .background(onSecondaryLight.copy(alpha = 0.1f)),
            ) {
                Image(
                    painter = painterResource(R.drawable.hospital),
                    contentDescription = "Foto medico",
                    modifier = Modifier
                )
            }

            Column {
                Text(
                    text = hospital.nombre,
                    fontSize = 17.sp,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 8.dp)
                )

                Text(
                    text = hospital.tipo,
                    fontSize = 13.sp,
                    fontFamily = FontFamily.Serif,
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
                            text = hospital.ubicacion,
                            fontSize = 13.sp,
                            fontFamily = FontFamily.Serif,
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

fun filtroMedicos(hospitalId: String): List<Medico>{
    return medicos.filter { it.idHospital == hospitalId }
}

data class Hospital(
    val id: String,
    val nombre: String,
    val tipo: String,
    val ubicacion: String
)