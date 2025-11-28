package com.rainbown.netmedicine.View

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.rainbown.netmedicine.Dataa.AuthRepositoryimplRegistree
import com.rainbown.netmedicine.Domainn.usecase.RegistreeUsecase


import com.rainbown.netmedicine.R
import com.rainbown.netmedicine.navegacion.ScreenNav
import com.rainbown.netmedicine.ui.theme.AppTypography
import com.rainbown.netmedicine.ui.theme.inverseSurfaceLightMediumContrast
import com.rainbown.netmedicine.ui.theme.onPrimaryContainerLight
import com.rainbown.netmedicine.ui.theme.primaryLight

import com.rainbown.netmedicine.viewmodel.RegistroVm
import com.rainbown.netmedicine.viewmodel.RegistroVmFactory


@Composable
fun pantallaregistro(navController: NavController, context: Context){
    val repository = AuthRepositoryimplRegistree(context)
    val registreUsecase = RegistreeUsecase(repository)
    val viewModel: RegistroVm = viewModel(
        factory = RegistroVmFactory(registreUsecase)
    )

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            Registro(navController, viewModel)
        }
    }
}
@Composable
fun Registro(navController: NavController,viewModel: RegistroVm) {
    val context = LocalContext.current
    val mensaje by viewModel.mensaje.observeAsState(initial = "")
    if (mensaje.isNotEmpty()) {
        android.widget.Toast.makeText(context, mensaje, android.widget.Toast.LENGTH_SHORT).show()
    }
    LaunchedEffect(mensaje) {
        if (mensaje == "Registro completado correctamente") {
            navController.navigate(route = ScreenNav.pantallalogin.route) {
                popUpTo(ScreenNav.pantallaregistro.route) { inclusive = true }
            }
        }
    }


    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val namevm: String by viewModel.nombre.observeAsState(initial ="")
        val lastnamevm: String by viewModel.lastName.observeAsState(initial ="")
        val Telvm: String by viewModel.tel.observeAsState(initial ="")
        val mailvm: String by viewModel.email.observeAsState(initial ="")
        val passwordvm: String by viewModel.password.observeAsState(initial ="")
        val generovm: String by viewModel.genero.observeAsState("")
        val pesovm: String by viewModel.peso.observeAsState("")
        val alturavm: String by viewModel.altura.observeAsState("")


        val (info, loginButton,registerButton) = createRefs()
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

        // botenes de abajoo

        Box(modifier = Modifier.constrainAs(loginButton) {
            top.linkTo(info.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }) {

            Column {
                Button(
                    onClick = {
                        viewModel.onRegistroClicked()
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
                        fontFamily = FontFamily.Serif,
                        letterSpacing = 1.2.sp,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W500,
                        style = AppTypography.labelLarge)
                }

                Spacer(modifier = Modifier.height(20.dp))

                OutlinedButton(
                    onClick = {
                        navController.navigate(route = ScreenNav.pantallainicial.route)
                    },
                    modifier = Modifier
                        .width(330.dp)
                        .height(55.dp)
                ) {
                    Text(
                        "Regresar al Inicio",
                        color = onPrimaryContainerLight,
                        fontFamily = FontFamily.Serif,
                        letterSpacing = 1.2.sp,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.W700,
                        style = AppTypography.labelLarge
                    )
                }
            }
        }

        Box(modifier = Modifier.constrainAs(info){
            start.linkTo(parent.start)
            top.linkTo(boxUser.bottom, margin = 10.dp)
            end.linkTo(parent.end)
        }){
            Column (modifier = Modifier.verticalScroll(rememberScrollState())) {


                OutlinedTextField(
                    value = namevm,
                    onValueChange = { newName ->
                        viewModel.nombre.value = newName
                    },
                    label = { Text("Nombre",
                        fontFamily = FontFamily.Serif,
                        letterSpacing = 1.2.sp,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.W400,
                        style = AppTypography.labelLarge) },

                    modifier = Modifier
                        .width(330.dp)
                        .height(55.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )
                OutlinedTextField(
                    value = lastnamevm,
                    onValueChange = { newLastname ->
                        viewModel.lastName.value = newLastname
                    },
                    label = { Text("Apellido",
                        fontFamily = FontFamily.Serif,
                        letterSpacing = 1.2.sp,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.W400,
                        style = AppTypography.labelLarge) },

                    modifier = Modifier
                        .width(330.dp)
                        .height(55.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )
                OutlinedTextField(
                    value = Telvm,
                    onValueChange = { Newtel ->
                        viewModel.tel.value = Newtel
                    },
                    label = { Text("Teléfono",
                        fontFamily = FontFamily.Serif,
                        letterSpacing = 1.2.sp,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.W400,
                        style = AppTypography.labelLarge) },

                    modifier = Modifier
                        .width(330.dp)
                        .height(55.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
                )
                OutlinedTextField(
                    value = generovm,
                    onValueChange = { newGenero ->
                        viewModel.genero.value = newGenero
                    },
                    label = { Text("Genero",
                        fontFamily = FontFamily.Serif,
                        letterSpacing = 1.2.sp,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.W400,
                        style = AppTypography.labelLarge) },

                    modifier = Modifier
                        .width(330.dp)
                        .height(55.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )
                OutlinedTextField(
                    value = pesovm,
                    onValueChange = { newPeso ->
                        viewModel.peso.value = newPeso
                    },
                    label = { Text("Peso",
                        fontFamily = FontFamily.Serif,
                        letterSpacing = 1.2.sp,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.W400,
                        style = AppTypography.labelLarge) },

                    modifier = Modifier
                        .width(330.dp)
                        .height(55.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )
                OutlinedTextField(
                    value = alturavm,
                    onValueChange = { newAltura ->
                        viewModel.altura.value = newAltura
                    },
                    label = { Text("Altura",
                        fontFamily = FontFamily.Serif,
                        letterSpacing = 1.2.sp,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.W400,
                        style = AppTypography.labelLarge) },

                    modifier = Modifier
                        .width(330.dp)
                        .height(55.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )
                OutlinedTextField(
                    value = mailvm,
                    onValueChange = { Newmail->
                        viewModel.email.value = Newmail
                    },
                    label = { Text("Correo electrónico",
                        fontFamily = FontFamily.Serif,
                        letterSpacing = 1.2.sp,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.W400,
                        style = AppTypography.labelLarge) },

                    modifier = Modifier
                        .width(330.dp)
                        .height(55.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )
                OutlinedTextField(
                    value = passwordvm,
                    onValueChange = {NewPass ->
                        viewModel.password.value = NewPass
                    },
                    label = { Text("Contraseña",
                        fontFamily = FontFamily.Serif,
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
        }



        Spacer(modifier = Modifier.width(15.dp))
    }
}