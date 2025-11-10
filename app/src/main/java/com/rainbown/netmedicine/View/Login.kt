package com.rainbown.netmedicine.View

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.rainbown.netmedicine.R
import com.rainbown.netmedicine.navegacion.ScreenNav
import com.rainbown.netmedicine.ui.theme.AppTypography
import com.rainbown.netmedicine.ui.theme.inverseSurfaceLightMediumContrast
import com.rainbown.netmedicine.ui.theme.primaryLight
import com.rainbown.netmedicine.viewmodel.LoginVM
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import com.rainbown.netmedicine.data.repository.AuthRepositoryImpl


@Composable
fun pantallalogin(navController: NavController){
    val context = LocalContext.current
    val repository = AuthRepositoryImpl(context)
    val loginUseCase = com.rainbown.netmedicine.domain.usecase.Login(repository)
    val viewModel = remember { LoginVM(loginUseCase) }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Login(navController, viewModel)
    }

}
@Composable
fun Login(navController: NavController,viewModel: LoginVM) {
    val context = LocalContext.current
    val usuario by viewModel.usuarioLiveData.observeAsState()
    val error by viewModel.errorLiveData.observeAsState()

    LaunchedEffect(usuario) {
        usuario?.let {
            navController.navigate(ScreenNav.pantallaprincipal.route) {
                popUpTo(ScreenNav.pantallalogin.route) { inclusive = true }
            }
        }
    }

    LaunchedEffect(error) {
        error?.let {
            android.widget.Toast.makeText(context, it, android.widget.Toast.LENGTH_LONG).show()
            viewModel.errorLiveData.value = null
        }
    }

    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val mailvm: String by viewModel.email.observeAsState(initial = "")
        val usuario by viewModel.usuarioLiveData.observeAsState()
        val passwordvm: String by viewModel.password.observeAsState(initial = "")
        val (emailField, passwordField, loginButton, registerButton) = createRefs()
        val (boxIcon) = createRefs()

        Box(modifier = Modifier.constrainAs(boxIcon) {
            top.linkTo(parent.top, margin = 50.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }) {
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
                value = mailvm,
                onValueChange = { Newmail ->
                    viewModel.email.value = Newmail
                },
                label = {
                    Text(
                        "Correo electrónico",
                        fontFamily = FontFamily.SansSerif,
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

//        Box(modifier = Modifier.constrainAs(userField) {
//            top.linkTo(emailField.bottom, margin = 25.dp)
//            start.linkTo(parent.start)
//            end.linkTo(parent.end)
//        }) {
//            OutlinedTextField(
//                value = usrvm,
//                onValueChange = { newUsr->
//
//                    viewModel.usr.value = newUsr
//                },
//                label = { Text("Usuario",
//                    fontFamily = FontFamily.SansSerif,
//                    letterSpacing = 1.2.sp,
//                    fontSize = 17.sp,
//                    textDecoration = TextDecoration.Underline,
//                    fontWeight = FontWeight.W400,
//                    style = AppTypography.labelLarge) },
//
//                modifier = Modifier
//                    .width(330.dp)
//                    .height(60.dp),
//                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
//            )
//        }

        Box(modifier = Modifier.constrainAs(passwordField) {
            top.linkTo(emailField.bottom, margin = 25.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }) {
            OutlinedTextField(
                value = passwordvm,
                onValueChange = { NewPass ->
                    viewModel.password.value = NewPass
                },
                label = {
                    Text(
                        "Contraseña",
                        fontFamily = FontFamily.SansSerif,
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

        // botenes de abajoo

        Box(modifier = Modifier.constrainAs(loginButton) {
            top.linkTo(passwordField.bottom, margin = 50.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }) {
            Button(
                onClick = {
                    viewModel.onLoginSelected()
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
                onClick = {
                    navController.navigate(route = ScreenNav.pantallaregistro.route)
                },
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