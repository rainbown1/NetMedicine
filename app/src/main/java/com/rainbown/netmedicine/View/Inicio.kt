package com.rainbown.netmedicine.View

import android.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.outlined.Markunread
import androidx.compose.material.icons.outlined.MedicalInformation
import androidx.compose.material.icons.outlined.Medication
import androidx.compose.material.icons.outlined.ViewAgenda
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.rainbown.netmedicine.View.Components.MyNavigationBar
import com.rainbown.netmedicine.View.Components.barra
import com.rainbown.netmedicine.navegacion.ScreenNav
import com.rainbown.netmedicine.ui.theme.onPrimaryContainerLight
import com.rainbown.netmedicine.ui.theme.onPrimaryLight
import com.rainbown.netmedicine.ui.theme.onSecondaryLight
import com.rainbown.netmedicine.ui.theme.primaryContainerLight
import com.rainbown.netmedicine.ui.theme.primaryLight
import com.rainbown.netmedicine.ui.theme.secondaryLight
import com.rainbown.netmedicine.viewmodel.LoginVM

@Composable
fun pantallaprincipal(navController: NavController){
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        inicio(navController)
    }
}
@Composable
fun inicio(navController: NavController){
    ConstraintLayout{
        val (barra,contenedor,menu) = createRefs()

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(primaryLight)
                .height(100.dp)
                .padding(30.dp)
                .padding(top = 20.dp)
                .constrainAs(barra){
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                }
        ) {
            Row {

                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = " ",
                    modifier = Modifier.size(width = 30.dp, height = 30.dp)
                )
                Text(
                    text = "Hola, Bienvenido! ",
                    fontFamily = FontFamily.Serif,
                    fontSize = 18.sp,
                    color = onPrimaryLight,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }



        Box(modifier = Modifier.fillMaxWidth().padding(30.dp).constrainAs(contenedor){
            top.linkTo(barra.bottom, margin = 100.dp)
            end.linkTo(parent.end, margin = 50.dp)
            start.linkTo(parent.start, margin = 50.dp)
        }){
            Column (modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)){
                //Icons y labels
                val icons = listOf(Icons.Outlined.ViewAgenda, Icons.Filled.MedicalServices, Icons.Outlined.Markunread,Icons.Outlined.Medication, Icons.Default.Receipt)
                val labels = listOf("Agenda", "Medicos", "Servicios","Hospitales", "Receta Medica")

                labels.forEachIndexed { index, titulo ->
                    ElevatedCard (
                        onClick = {

                            when(titulo){
                                "Agenda" -> navController.navigate(route = ScreenNav.pantallaagenda.route)
                                "Hospitales" -> navController.navigate(route= ScreenNav.pantallahospitales.route)
                                "Medicos" -> navController.navigate(route= ScreenNav.pantallahospitales.route)
                                "Servicios" -> navController.navigate(route = ScreenNav.pantallaservicios.route)
                                "Receta Medica" -> navController.navigate(route = ScreenNav.pantallarecetas.route)
                            }
                        },
                        elevation = CardDefaults.cardElevation(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                    ){
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = icons[index],
                                contentDescription = titulo,
                                tint = primaryLight
                            )
                            Text(
                                text = titulo,
                                modifier = Modifier.padding(start = 8.dp),
                                fontSize = 16.sp
                            )
                        }
                    }
                }

            } // Column
        } //Box

        Box(modifier = Modifier.background(primaryLight).fillMaxWidth().constrainAs(menu){
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }){
            MyNavigationBar(navController)
        }//Box

    }
}