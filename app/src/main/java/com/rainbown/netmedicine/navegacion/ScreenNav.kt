package com.rainbown.netmedicine.navegacion

sealed class ScreenNav(val route: String) {
 object pantallainicial: ScreenNav("pantalla_inicial")
 object pantallalogin: ScreenNav("pantalla_login")
 object pantallaregistro: ScreenNav("pantalla_registro")
 object  pantallaprincipal: ScreenNav("pantalla_principal")
 object  pantallaservicios: ScreenNav("pantalla_servicios")
 object pantallahospitales: ScreenNav("pantalla_hospitales")
 object pantallaagenda: ScreenNav("pantalla_agenda")
 object pantallarecetas: ScreenNav("pantalla_recetas")
 object pantallaperfil: ScreenNav("pantalla_perfil")
 object pantallacamara: ScreenNav("pantalla-camara")

}