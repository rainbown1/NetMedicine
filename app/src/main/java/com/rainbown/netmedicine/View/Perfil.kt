package com.rainbown.netmedicine.View

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.rainbown.netmedicine.R
import com.rainbown.netmedicine.View.Components.MyNavigationBar
import com.rainbown.netmedicine.View.Components.barra

@Composable
fun pantallaperfil(navController: NavController){
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        PerfilScreen(navController, modifier = Modifier.padding(innerPadding))
    }
}

@Composable
fun PerfilScreen(navController: NavController, modifier: Modifier){
    ConstraintLayout (modifier = Modifier.fillMaxSize() ){
        val (barra,img,content,menu) = createRefs()
        Box(
            modifier = Modifier.constrainAs(barra){
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                end.linkTo(parent.end)
            }
        ){
            barra("Perfil")
        }

        Box(modifier = Modifier.constrainAs(img){
            start.linkTo(parent.start, margin = 15.dp)
            top.linkTo(barra.bottom, margin = 15.dp)
        }){
            Row {
                Image(
                    painter = painterResource(R.drawable.user),
                    contentDescription = "Foto de usuario",
                    modifier = Modifier.size(80.dp)
                )
                Column {
                    Text("Nombre del usuario")
                    Text("Correo del usuario")
                }
            }
        }

        Box(modifier = Modifier.constrainAs(content){
            start.linkTo(parent.start)
            top.linkTo(img.bottom, margin = 15.dp)
            end.linkTo(parent.end)
        }){

        }


        Box(modifier = Modifier.constrainAs(menu){
            start.linkTo(parent.start)
            bottom.linkTo(parent.bottom)
            end.linkTo(parent.end)
        }){
            MyNavigationBar(navController)
        }
    }
}

