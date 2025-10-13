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
import androidx.compose.ui.text.font.FontWeight
import com.rainbown.netmedicine.R
import com.rainbown.netmedicine.ui.theme.onPrimaryContainerLight
import com.rainbown.netmedicine.ui.theme.primaryContainerLight

@Composable
fun Login(modifier: Modifier = Modifier) {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (emailField, passwordField, loginButton, registerButton) = createRefs()
        val (boxIcon) = createRefs()

        Box(modifier = Modifier.constrainAs(boxIcon){
            top.linkTo(parent.top, margin = 50.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }){
            Image(
                painter = painterResource(R.drawable.icon),
                contentDescription = " ",
                modifier = Modifier
                    .width(200.dp)
                    .height(180.dp)
            )
        }

        Box(modifier = Modifier.constrainAs(emailField) {
            top.linkTo(parent.top, margin = 250.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }) {
            OutlinedTextField(
                value = email.value,
                onValueChange = { email.value = it },
                label = { Text("Correo electrónico") },
                modifier = Modifier
                    .width(250.dp)
                    .height(60.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )
        }

        Box(modifier = Modifier.constrainAs(passwordField) {
            top.linkTo(emailField.bottom, margin = 20.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }) {
            OutlinedTextField(
                value = password.value,
                onValueChange = { password.value = it },
                label = { Text("Contraseña") },
                modifier = Modifier
                    .width(250.dp)
                    .height(60.dp),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
        }

        Box(modifier = Modifier.constrainAs(loginButton) {
            top.linkTo(passwordField.bottom, margin = 30.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }) {
            // botenes de abajoo
            Button(
                onClick = {
                    println("Login: ${email.value}")
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = primaryContainerLight
                ),
                modifier = Modifier
                    .width(250.dp)
                    .height(50.dp)
            ) {
                Text(
                    "Iniciar Sesión",
                    color = onPrimaryContainerLight,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                )
            }
        }

        Box(modifier = Modifier.constrainAs(registerButton) {
            top.linkTo(loginButton.bottom, margin = 15.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }) {
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(
                    containerColor = primaryContainerLight
                ),
                modifier = Modifier
                    .width(250.dp)
                    .height(50.dp)
            ) {
                Text(
                    "Registrarse",
                    color = onPrimaryContainerLight,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,

                )

            }
        }
    }
}
