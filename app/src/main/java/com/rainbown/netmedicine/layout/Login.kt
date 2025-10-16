package com.rainbown.netmedicine.layout

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import com.rainbown.netmedicine.R
import com.rainbown.netmedicine.ui.theme.AppTypography
import com.rainbown.netmedicine.ui.theme.inverseSurfaceLightMediumContrast
import com.rainbown.netmedicine.ui.theme.onPrimaryContainerLight
import com.rainbown.netmedicine.ui.theme.primaryContainerLight
import com.rainbown.netmedicine.ui.theme.primaryLight
import com.rainbown.netmedicine.ui.theme.tertiaryLightMediumContrast


@Composable
fun Login(modifier: Modifier = Modifier) {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val user = remember { mutableStateOf("") }

    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (emailField, passwordField, userField, loginButton, registerButton) = createRefs()
        val (boxIcon) = createRefs()

        Box(modifier = Modifier.constrainAs(boxIcon){
            top.linkTo(parent.top, margin = 50.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }){
            Image(
                painter = painterResource(R.drawable.logo),
                contentDescription = " ",
                modifier = Modifier
                    .width(200.dp)
                    .height(180.dp)
            )
        }

        Box(modifier = Modifier.constrainAs(emailField) {
            top.linkTo(parent.top, margin = 260.dp)
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
                    textDecoration = TextDecoration.Underline,
                    fontWeight = FontWeight.W400,
                    style = AppTypography.labelLarge) },

                modifier = Modifier
                    .width(330.dp)
                    .height(60.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )
        }

        Box(modifier = Modifier.constrainAs(userField) {
            top.linkTo(emailField.bottom, margin = 25.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }) {
            OutlinedTextField(
                value = user.value,
                onValueChange = { user.value = it },
                label = { Text("Usuario",
                    fontFamily = FontFamily.SansSerif,
                    letterSpacing = 1.2.sp,
                    fontSize = 17.sp,
                    textDecoration = TextDecoration.Underline,
                    fontWeight = FontWeight.W400,
                    style = AppTypography.labelLarge) },

                modifier = Modifier
                    .width(330.dp)
                    .height(60.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )
        }

        Box(modifier = Modifier.constrainAs(passwordField) {
            top.linkTo(userField.bottom, margin = 25.dp)
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
                    textDecoration = TextDecoration.Underline,
                    fontWeight = FontWeight.W400,
                    style = AppTypography.labelLarge) },

                modifier = Modifier
                    .width(330.dp)
                    .height(60.dp),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
        }

        // botenes de abajoo

        Box(modifier = Modifier.constrainAs(loginButton) {
            top.linkTo(passwordField.bottom, margin = 50.dp)
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
                Text(
                    "Iniciar Sesión",
                    color = inverseSurfaceLightMediumContrast,
                    fontFamily = FontFamily.SansSerif,
                    letterSpacing = 1.2.sp,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.W500,
                    style = AppTypography.labelLarge
                )
            }
        }

        Box(modifier = Modifier.constrainAs(registerButton) {
            top.linkTo(loginButton.bottom, margin = 30.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }) {
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(
                    containerColor = primaryLight
                ),
                modifier = Modifier
                    .width(330.dp)
                    .height(55.dp)
            ) {
                Text(
                    "Registrarse",
                    color = inverseSurfaceLightMediumContrast,
                    fontFamily = FontFamily.SansSerif,
                    letterSpacing = 1.2.sp,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.W500,
                    textDecoration = TextDecoration.Underline,
                    style = AppTypography.labelLarge
                )

            }
        }
    }
}
