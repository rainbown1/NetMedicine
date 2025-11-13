package com.rainbown.netmedicine.navegacion
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable

import androidx.compose.ui.platform.LocalContext

import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rainbown.netmedicine.View.PantallaMedicosPorHospital
import com.rainbown.netmedicine.View.hospitales
import com.rainbown.netmedicine.View.pantallaagendas
import com.rainbown.netmedicine.View.pantallahospitales
import com.rainbown.netmedicine.View.pantallainicial
import com.rainbown.netmedicine.View.pantallalogin
import com.rainbown.netmedicine.View.pantallaprincipal
import com.rainbown.netmedicine.View.pantallaregistro
import com.rainbown.netmedicine.View.pantallaservicios


@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun Nav() {
    val navController = rememberNavController()
    val context = LocalContext.current
    NavHost(
        navController = navController,
        startDestination = ScreenNav.pantallainicial.route
    ) {
        composable(route = ScreenNav.pantallainicial.route) {
            pantallainicial(navController)
        }
        composable ( route= ScreenNav.pantallalogin.route ){
            pantallalogin(navController)
        }
        composable ( route= ScreenNav.pantallaregistro.route ){
            pantallaregistro(navController, context = context )
        }
        composable ( route= ScreenNav.pantallaprincipal.route ){
            pantallaprincipal(navController)
        }
        composable(route= ScreenNav.pantallaservicios.route){
        pantallaservicios(navController)
        }
        composable(route= ScreenNav.pantallaagenda.route){
            pantallaagendas(navController)
        }
        composable (route = ScreenNav.pantallahospitales.route){
            pantallahospitales(navController)
        }
        composable(route= "medicos/{hospitalId}") { backStackEntry ->
            val hospitalId = backStackEntry.arguments?.getString("hospitalId")
            PantallaMedicosPorHospital(navController, hospitalId)
        }
    }
}

