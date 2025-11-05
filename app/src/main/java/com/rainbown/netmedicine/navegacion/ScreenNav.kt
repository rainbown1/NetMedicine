package com.rainbown.netmedicine.navegacion

sealed class ScreenNav(val route: String) {
 object pantallainicial: ScreenNav("pantalla_inicial")
 object pantallalogin: ScreenNav("pantalla_login")
 object pantallaregistro: ScreenNav("pantalla_registro")
 object  pantallaprincipal: ScreenNav("pantalla_principal")
}