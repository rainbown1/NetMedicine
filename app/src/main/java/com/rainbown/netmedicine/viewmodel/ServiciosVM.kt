package com.rainbown.netmedicine.viewmodel

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.rainbown.netmedicine.View.Components.barra
import com.rainbown.netmedicine.View.filtroServicio
import com.rainbown.netmedicine.View.hospitales
import com.rainbown.netmedicine.View.servicios

data class InfoService(
    val funciones: String,
    val acciones: String,
    val boton: String
)

@Composable
fun InfoService(navController: NavController, servicioId: String?) {
    ConstraintLayout {
        val (barra,titulo,descripcion,boton,menu) = createRefs()

        val serviciosFiltrados = remember (servicioId){
            if (servicioId != null){
                filtroServicio(servicioId)
            } else {
                emptyList()
            }
        }

        val nombreServicio = remember(servicioId) {
            servicios.find { it.servicioId == servicioId}?.nombre ?: "Servicio"
        }

        Box(modifier = Modifier.constrainAs(barra){
            start.linkTo(parent.start)
            top.linkTo(parent.top)
            end.linkTo(parent.end)
        }){
            barra("Servicio -> $nombreServicio")
        }

        Box(modifier = Modifier.constrainAs(titulo){
          start.linkTo(parent.start)
          top.linkTo(barra.bottom, margin = 15.dp)
          end.linkTo(parent.end)
        }){

        }
    }
}