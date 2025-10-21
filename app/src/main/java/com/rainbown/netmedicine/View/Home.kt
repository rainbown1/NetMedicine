package com.rainbown.netmedicine.View

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.internal.NavContext
import com.rainbown.netmedicine.R
import com.rainbown.netmedicine.navegacion.ScreenNav
import com.rainbown.netmedicine.ui.theme.AppTypography
import com.rainbown.netmedicine.ui.theme.inverseSurfaceLightMediumContrast
import com.rainbown.netmedicine.ui.theme.onPrimaryContainerLight
import com.rainbown.netmedicine.ui.theme.primaryLight

@Composable
fun pantallainicial(navController: NavController){
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
       home(navController)
    }
}
@Composable
fun home(navController: NavController) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()){
    val (boxIcon, boxButton1, boxButton2, bienvenida) = createRefs()
        Box(modifier = Modifier.constrainAs(bienvenida) {
            top.linkTo(parent.top, margin = 70.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }) {
            Text(
                "BIENVENIDO",
                color = primaryLight,
                fontFamily = FontFamily.SansSerif,
                letterSpacing = 1.2.sp,
                fontSize = 50.sp,
                fontWeight = FontWeight.W900
            )
        }

        Box(modifier = Modifier.constrainAs(boxIcon){
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            bottom.linkTo(parent.bottom, margin = 140.dp)
            end.linkTo(parent.end)
        }){
            Image(
                painter = painterResource(R.drawable.logo),
                contentDescription = " ",
                modifier = Modifier
                    .width(250.dp)
                    .height(300.dp)
            )
        }
        Box(modifier = Modifier.constrainAs(boxButton1){
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(parent.bottom, margin = 160.dp)
        }){
            Button(
                onClick = {
                    navController.navigate(route = ScreenNav.pantallalogin.route)
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
        Box(modifier = Modifier.constrainAs(boxButton2){
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(parent.bottom, margin = 80.dp)
        }){
            Button(
                onClick = {  navController.navigate(route = ScreenNav.pantallaregistro.route) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                ),
                modifier = Modifier
                    .width(330.dp)
                    .height(55.dp)
            ) {
                Text("Registrarse",
                    color = onPrimaryContainerLight,
                    fontFamily = FontFamily.SansSerif,
                    letterSpacing = 1.2.sp,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W500,
                    textDecoration = TextDecoration.Underline,
                    style = AppTypography.labelLarge)
            }
        }
    }
}


