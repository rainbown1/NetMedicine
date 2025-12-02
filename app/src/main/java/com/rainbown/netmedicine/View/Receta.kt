package com.rainbown.netmedicine.View

import ObtenerRecetasUseCase
import RecetEntity
import RecetaVM
import RecetaVMFactory
import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.rainbown.netmedicine.Dataa.ReepositoryimplRecet

import com.rainbown.netmedicine.View.Components.MyNavigationBar
import com.rainbown.netmedicine.View.Components.barra





@Composable
fun pantallarecetas(
    navController: NavController,
    context: Context

) {
    val repository = ReepositoryimplRecet(context)
    val useCase = ObtenerRecetasUseCase(repository)

    val viewModel: RecetaVM = viewModel(
        factory = RecetaVMFactory(useCase)
    )

    val recetas by viewModel.recetas.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()

    LaunchedEffect(Unit) {
        val sharedPref = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val idPaciente = sharedPref.getInt("id_paciente",-1)
        println("ID obtenido de prefs: $idPaciente")
        viewModel.cargarRecetas(idPaciente)


    }


    RecetaScreen(
        navController = navController,
        recetas = recetas,
        loading = loading,
        error = error
    )
}


@Composable
fun RecetaScreen(
    navController: NavController,
    recetas: List<RecetEntity>,
    loading: Boolean,
    error: String?,
    modifier: Modifier = Modifier
) {
    ConstraintLayout(modifier = modifier.fillMaxSize()) {

        val (barraTop, content, menu) = createRefs()


        Box(
            modifier = Modifier.constrainAs(barraTop) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                end.linkTo(parent.end)
            }
        ) {
            barra("Recetas Médicas")
        }


        Box(
            modifier = Modifier.constrainAs(content) {
                start.linkTo(parent.start)
                top.linkTo(barraTop.bottom, margin = 250.dp)
                end.linkTo(parent.end)
                bottom.linkTo(menu.top)
            }
        ) {
            when {
                loading -> Text(
                    "Cargando recetas...",
                    modifier = Modifier.padding(20.dp),
                    fontSize = 16.sp
                )

                error != null -> Text(
                    "Error: $error",
                    modifier = Modifier.padding(20.dp),
                    fontSize = 16.sp,
                    color = Color.Red
                )

                recetas.isEmpty() -> Text(
                    "No hay recetas registradas.",
                    modifier = Modifier.padding(20.dp),
                    fontSize = 16.sp
                )

                else -> LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(15.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(recetas) { receta ->
                        println("Mostrando receta: ${receta.medicamento}")
                        RecetaCard(receta)
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
fun RecetaCard(receta: RecetEntity) {
    Card(
        onClick = {},
        modifier = Modifier.height(190.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.Center
        ) {

            // Medicamento
            Text(
                text = receta.medicamento,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 6.dp)
            )

            // Fecha
            Text(
                text = "Fecha: ${receta.fecha}",
                fontSize = 11.sp,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Cantidad y Frecuencia
            Row {
                Text(
                    text = "Cantidad: ${receta.cantidad}",
                    fontSize = 12.sp,
                    modifier = Modifier.padding(end = 10.dp)
                )

                Text(
                    text = "Frecuencia: ${receta.frecuencia}",
                    fontSize = 12.sp
                )
            }

            // Duración
            Text(
                text = "Duración: ${receta.duracion}",
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 4.dp)
            )

            // Instrucciones
            Text(
                text = "Indicaciones: ${receta.instruccion}",
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 6.dp)
            )
        }
    }
}

