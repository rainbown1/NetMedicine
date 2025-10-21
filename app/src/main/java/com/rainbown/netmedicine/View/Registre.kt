package com.rainbown.netmedicine.View

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.navigation.NavController
import com.rainbown.netmedicine.R
import com.rainbown.netmedicine.navegacion.ScreenNav
import com.rainbown.netmedicine.ui.theme.AppTypography
import com.rainbown.netmedicine.ui.theme.inverseSurfaceLightMediumContrast
import com.rainbown.netmedicine.ui.theme.onPrimaryContainerLight
import com.rainbown.netmedicine.ui.theme.primaryLight
@Composable
fun pantallaregistro(navController: NavController){
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
       Registro(navController)
    }
}
@Composable
fun Registro(navController: NavController) {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val name = remember { mutableStateOf("") }
    val ap = remember { mutableStateOf("") }
    val tel = remember { mutableStateOf("") }

    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (nameField, apField, telField, emailField, passwordField, loginButton,registerButton) = createRefs()
        val (boxUser) = createRefs()

        Box(modifier = Modifier.constrainAs(boxUser){
            top.linkTo(parent.top, margin = 50.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }){
            Image(
                painter = painterResource(R.drawable.user),
                contentDescription = " ",
                modifier = Modifier
                    .width(180.dp)
                    .height(150.dp)
            )
        }

        Box(modifier = Modifier.constrainAs(nameField) {
            top.linkTo(parent.top, margin = 250.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }) {
            OutlinedTextField(
                value = name.value,
                onValueChange = { name.value = it },
                label = { Text("Nombre",
                    fontFamily = FontFamily.SansSerif,
                    letterSpacing = 1.2.sp,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.W400,
                    style = AppTypography.labelLarge) },

                modifier = Modifier
                    .width(330.dp)
                    .height(55.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )
        }

        Box(modifier = Modifier.constrainAs(apField) {
            top.linkTo(nameField.bottom, margin = 25.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }) {
            OutlinedTextField(
                value = ap.value,
                onValueChange = { ap.value = it },
                label = { Text("Apellido",
                    fontFamily = FontFamily.SansSerif,
                    letterSpacing = 1.2.sp,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.W400,
                    style = AppTypography.labelLarge) },

                modifier = Modifier
                    .width(330.dp)
                    .height(55.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )
        }

        Box(modifier = Modifier.constrainAs(telField) {
            top.linkTo(apField.bottom, margin = 25.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }) {
            OutlinedTextField(
                value = tel.value,
                onValueChange = { tel.value = it },
                label = { Text("Teléfono",
                    fontFamily = FontFamily.SansSerif,
                    letterSpacing = 1.2.sp,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.W400,
                    style = AppTypography.labelLarge) },

                modifier = Modifier
                    .width(330.dp)
                    .height(55.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
            )
        }

        Box(modifier = Modifier.constrainAs(emailField) {
            top.linkTo(telField.bottom, margin = 25.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }) {
            OutlinedTextField(
                value = email.value,
                onValueChange = { email.value = it },
                label = { Text("Correo electrónico",
                    fontFamily = FontFamily.SansSerif,
                    letterSpacing = 1.2.sp,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.W400,
                    style = AppTypography.labelLarge) },

                modifier = Modifier
                    .width(330.dp)
                    .height(55.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )
        }

        Box(modifier = Modifier.constrainAs(passwordField) {
            top.linkTo(emailField.bottom, margin = 25.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }) {
            OutlinedTextField(
                value = password.value,
                onValueChange = { password.value = it },
                label = { Text("Contraseña",
                    fontFamily = FontFamily.SansSerif,
                    letterSpacing = 1.2.sp,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.W400,
                    style = AppTypography.labelLarge) },
                modifier = Modifier
                    .width(330.dp)
                    .height(55.dp),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
        }
        // botenes de abajoo

        Box(modifier = Modifier.constrainAs(loginButton) {
            top.linkTo(passwordField.bottom, margin = 45.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }) {

            Button(
                onClick = {
                    println("Login: ${email.value}")
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = primaryLight
                ),
                modifier = Modifier
                    .width(330.dp)
                    .height(55.dp)
            ) {
                Text("Iniciar Sesion",
                    color = inverseSurfaceLightMediumContrast,
                    fontFamily = FontFamily.SansSerif,
                    letterSpacing = 1.2.sp,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W500,
                    style = AppTypography.labelLarge)
            }
        }

        Box(modifier = Modifier.constrainAs(registerButton) {
            top.linkTo(loginButton.bottom, margin = 25.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }) {
            Button(
                onClick = {
                    navController.navigate(route = ScreenNav.pantallainicial.route)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                ),
                modifier = Modifier
                    .width(330.dp)
                    .height(55.dp)
            ) {
                Text(
                    "Regresar al Inicio",
                    color = onPrimaryContainerLight,
                    fontFamily = FontFamily.SansSerif,
                    letterSpacing = 1.2.sp,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.W700,
                    textDecoration = TextDecoration.Underline,
                    style = AppTypography.labelLarge
                    )
            }
        }
    }
}
