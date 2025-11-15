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
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.rainbown.netmedicine.R
import com.rainbown.netmedicine.data.repository.AuthRepositoryImpl
import com.rainbown.netmedicine.domain.usecase.LoginUsecase
import com.rainbown.netmedicine.navegacion.ScreenNav
import com.rainbown.netmedicine.ui.theme.AppTypography
import com.rainbown.netmedicine.ui.theme.inverseSurfaceLightMediumContrast
import com.rainbown.netmedicine.ui.theme.primaryLight
import com.rainbown.netmedicine.viewmodel.LoginVM

import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import android.widget.Toast

@Composable
fun pantallalogin(navController: NavController) {
    val context = LocalContext.current
    val repository = AuthRepositoryImpl(context)
    val loginUseCase = LoginUsecase(repository)


    val viewModel: LoginVM = viewModel(factory = LoginVM.LoginVMFactory(loginUseCase, context))


    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Login(navController, viewModel)
    }
}

@Composable
fun Login(navController: NavController, viewModel: LoginVM) {
    val context = LocalContext.current
    val usuario by viewModel.usuarioLiveData.observeAsState()
    val error by viewModel.errorLiveData.observeAsState()
    val mailvm by viewModel.email.observeAsState("")
    val passwordvm by viewModel.password.observeAsState("")


    LaunchedEffect(usuario) {
        usuario?.let {
            navController.navigate(ScreenNav.pantallaprincipal.route) {
                popUpTo(ScreenNav.pantallalogin.route) { inclusive = true }
            }
        }
    }


    LaunchedEffect(error) {
        error?.let {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            viewModel.clearError()
        }
    }

    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (boxIcon, emailField, passwordField, loginButton, registerButton) = createRefs()

        Box(modifier = Modifier.constrainAs(boxIcon) {
            top.linkTo(parent.top, margin = 50.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }) {
            Image(
                painter = painterResource(R.drawable.logo),
                contentDescription = "Logo",
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
                value = mailvm,
                onValueChange = { viewModel.email.value = it },
                label = {
                    Text(
                        "Correo electrónico",
                        fontFamily = FontFamily.Serif,
                        letterSpacing = 1.2.sp,
                        fontSize = 17.sp,
                        textDecoration = TextDecoration.Underline,
                        fontWeight = FontWeight.W400,
                        style = AppTypography.labelLarge
                    )
                },
                modifier = Modifier
                    .width(330.dp)
                    .height(60.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )
        }

        Box(modifier = Modifier.constrainAs(passwordField) {
            top.linkTo(emailField.bottom, margin = 25.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }) {
            OutlinedTextField(
                value = passwordvm,
                onValueChange = { viewModel.password.value = it },
                label = {
                    Text(
                        "Contraseña",
                        fontFamily = FontFamily.Serif,
                        letterSpacing = 1.2.sp,
                        fontSize = 17.sp,
                        textDecoration = TextDecoration.Underline,
                        fontWeight = FontWeight.W400,
                        style = AppTypography.labelLarge
                    )
                },
                modifier = Modifier
                    .width(330.dp)
                    .height(60.dp),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
        }

        Box(modifier = Modifier.constrainAs(loginButton) {
            top.linkTo(passwordField.bottom, margin = 50.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }) {
            Button(
                onClick = { viewModel.onLoginSelected() },
                colors = ButtonDefaults.buttonColors(containerColor = primaryLight),
                modifier = Modifier
                    .width(330.dp)
                    .height(55.dp)
            ) {
                Text(
                    "Iniciar Sesión",
                    color = inverseSurfaceLightMediumContrast,
                    fontFamily = FontFamily.Serif,
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
                onClick = {
                    navController.navigate(route = ScreenNav.pantallaregistro.route)
                },
                colors = ButtonDefaults.buttonColors(containerColor = primaryLight),
                modifier = Modifier
                    .width(330.dp)
                    .height(55.dp)
            ) {
                Text(
                    "Registrarse",
                    color = inverseSurfaceLightMediumContrast,
                    fontFamily = FontFamily.Serif,
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
