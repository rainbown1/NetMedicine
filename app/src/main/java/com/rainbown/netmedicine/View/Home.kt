package com.rainbown.netmedicine.View

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.rainbown.netmedicine.R
import com.rainbown.netmedicine.ui.theme.onPrimaryContainerLight
import com.rainbown.netmedicine.ui.theme.primaryContainerLight

@Composable

fun home(modifier: Modifier) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()){
    val (boxIcon, botonInicio, botonRegistro) = createRefs()
        Box(modifier = Modifier.constrainAs(boxIcon){
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            bottom.linkTo(parent.bottom, margin = 150.dp)
            end.linkTo(parent.end)
        }){
            Image(
                painter = painterResource(R.drawable.icon),
                contentDescription = " ",
                modifier = Modifier
                    .width(500.dp)
                    .height(300.dp)
            )
        }
        Box(modifier = Modifier.constrainAs(botonInicio){
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(parent.bottom, margin = 200.dp)
        }){
            Button(
                onClick = { },
                colors = ButtonDefaults.buttonColors(
                    containerColor = primaryContainerLight
                ),
                modifier = Modifier
                    .width(250.dp)
                    .height(50.dp)
            ) {
                Text("Iniciar Sesion", color = onPrimaryContainerLight, fontSize = 12.sp, fontFamily = FontFamily.Serif)
            }
        }
        Box(modifier = Modifier.constrainAs(botonRegistro){
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(parent.bottom, margin = 145.dp)
        }){
            OutlinedButton(
                onClick = { },
                modifier = Modifier
                    .width(250.dp)
                    .height(50.dp)
            ) {
                Text("Registrarse", color = onPrimaryContainerLight, fontSize = 12.sp, fontFamily = FontFamily.Serif)
            }
        }
    }
}


