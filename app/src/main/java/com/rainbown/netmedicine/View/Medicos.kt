package com.rainbown.netmedicine.View

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Whatsapp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.rainbown.netmedicine.R
import com.rainbown.netmedicine.View.Components.MyNavigationBar
import com.rainbown.netmedicine.View.Components.barra
import com.rainbown.netmedicine.Domainn.entity.MedicoEntity
import com.rainbown.netmedicine.viewmodel.MedicosVM
import com.rainbown.netmedicine.viewmodel.MedicosVMFactory
import com.rainbown.netmedicine.ui.theme.onSecondaryLight

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PantallaMedicosPorHospital(navController: NavController, hospitalId: String?) {
    Scaffold(modifier = Modifier.fillMaxSize()) {
        Medicos(
            modifier = Modifier.padding(it),
            navController = navController,
            hospitalId = hospitalId
        )
    }
}

@Composable
fun Medicos(modifier: Modifier, navController: NavController, hospitalId: String?) {

    val context = LocalContext.current
    val viewModel: MedicosVM = viewModel(factory = MedicosVMFactory(context))

    val listaMedicos by viewModel.medicosLiveData.observeAsState(emptyList())
    val error by viewModel.errorLiveData.observeAsState()

    LaunchedEffect(Unit) {
        viewModel.cargarMedicos()
    }


    val medicosFiltrados = listaMedicos.filter { it.idHospital == hospitalId }

    ConstraintLayout(modifier = Modifier.fillMaxSize()) {

        val (barra, contenedor, menu) = createRefs()

        Box(
            modifier = Modifier.constrainAs(barra) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                end.linkTo(parent.end)
            }
        ) {
            barra("Médicos del hospital $hospitalId")
        }

        Box(
            modifier = Modifier.constrainAs(contenedor) {
                start.linkTo(parent.start)
                top.linkTo(barra.bottom, margin = 250.dp)
                end.linkTo(parent.end)
                bottom.linkTo(menu.top)
            }
        ) {

            when {
                error != null -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Error al cargar médicos\n$error",
                            color = Color.Red,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                medicosFiltrados.isEmpty() -> {
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
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(15.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(medicosFiltrados) { medico ->
                            MedicosCards(medico = medico)
                        }
                    }
                }
            }
        }

        Box(
            modifier = Modifier.constrainAs(menu) {
                start.linkTo(parent.start)
                bottom.linkTo(parent.bottom)
                end.linkTo(parent.end)
            }
        ) {
            MyNavigationBar(navController)
        }
    }
}

@Composable
fun MedicosCards(medico: MedicoEntity) {

    Card(
        onClick = {
            println("Medico: ${medico.nombre}")
        },
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier.height(140.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            contentColor = Color.White,
            containerColor = onSecondaryLight
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp)
        ) {

            // Imagen del médico
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(onSecondaryLight.copy(alpha = 0.1f), RoundedCornerShape(10.dp))
            ) {
                Image(
                    painter = painterResource(R.drawable.medico),
                    contentDescription = "Foto medico"
                )
            }

            Column(modifier = Modifier.padding(start = 10.dp)) {

                Text(
                    text = medico.nombre,
                    fontSize = 17.sp,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 8.dp)
                )

                Text(
                    text = medico.especialidad,
                    fontSize = 13.sp,
                    fontFamily = FontFamily.Serif,
                    color = Color.DarkGray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 4.dp),
                    maxLines = 2
                )

                Row {

                    OutlinedButton(onClick = { }) {
                        Row {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = "Ubicacion de hospital"
                            )
                            Text(
                                text = medico.idHospital,
                                fontSize = 13.sp,
                                fontFamily = FontFamily.Serif,
                                color = Color.DarkGray,
                                modifier = Modifier.padding(start = 4.dp)
                            )
                        }
                    }

                    OutlinedButton(onClick = { }) {
                        Row {
                            Icon(
                                imageVector = Icons.Default.Whatsapp,
                                contentDescription = "Contacto"
                            )
                            Text(
                                text = medico.contacto,
                                fontSize = 13.sp,
                                fontFamily = FontFamily.Serif,
                                color = Color.DarkGray,
                                modifier = Modifier.padding(start = 4.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
