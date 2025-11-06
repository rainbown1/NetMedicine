package com.rainbown.netmedicine.View.Components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocalHospital
import androidx.compose.material.icons.outlined.MedicalServices
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.rainbown.netmedicine.navegacion.ScreenNav

@Composable
fun MyNavigationBar(navController: NavController){
    var selectedItem by remember { mutableIntStateOf(0) }
    val items = listOf("Inicio", "Hospitales", "Servicios", "Perfil")
    val selectedIcons = listOf(Icons.Filled.Home, Icons.Filled.LocalHospital, Icons.Filled.MedicalServices, Icons.Filled.Person)
    val unselectedItems = listOf(Icons.Outlined.Home, Icons.Outlined.LocalHospital, Icons.Outlined.MedicalServices, Icons.Outlined.Person)

    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        if (selectedItem == index ) selectedIcons[index] else unselectedItems[index],
                        contentDescription = item,
                    )
                },
                label = { Text(item) },
                selected = selectedItem == index,
                onClick = {
                    when(item){
                        "Agenda" -> navController.navigate(route = ScreenNav.pantallaagenda.route)
                        //"Hospitales" -> navController.navigate("hospitalesScreen")
                        //"Medicos" -> navController.navigate("medicoScreen")
                        "Servicios" -> navController.navigate(route = ScreenNav.pantallaservicios.route)
                    }
                }

            )
        }

    }
}