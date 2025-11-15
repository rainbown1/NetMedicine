package com.rainbown.netmedicine.View

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.rainbown.netmedicine.View.Components.MyNavigationBar
import com.rainbown.netmedicine.View.Components.barra

@Composable
fun pantallarecetas(navController: NavController){
    Scaffold (modifier = Modifier.fillMaxSize()){innerPadding ->
        RecetaScreen(navController, modifier = Modifier.padding(innerPadding))
    }
}
@Composable
fun RecetaScreen(navController: NavController, modifier: Modifier){
    ConstraintLayout {
        val (barra,content,menu) = createRefs()
        Box(
            modifier = Modifier.constrainAs(barra){
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                end.linkTo(parent.end)
            }
        ){
            barra("Recetas Medicas")
        }
        Box(modifier = Modifier.constrainAs(content){
            start.linkTo(parent.start)
            top.linkTo(barra.bottom)
            end.linkTo(parent.end)
        }){
            LazyColumn(modifier = Modifier
                .fillMaxSize()
                .padding(15.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),)
            {
                items(recetaInfo){  receta->
                    RecetaCard(receta = receta)
                }
            }
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

@Composable
fun RecetaCard(receta: Receta){
    Card(
        onClick = { },
        modifier = Modifier
            .height(160.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
//            horizontalAlignment = Alignment.CenterHorizontally
            verticalArrangement = Arrangement.Center
        )
        {
            Text(
                text = receta.medicamento,
                fontSize = 12.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 5.dp)
            )
            Row {
                Text(
                    text = receta.cantidad,
                    fontSize = 12.sp,
                    fontFamily = FontFamily.SansSerif,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 8.dp)
                )
                Text(
                    text = receta.admin,
                    fontSize = 12.sp,
                    fontFamily = FontFamily.SansSerif,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
            Text(
                text = receta.admin,
                fontSize = 12.sp,
                fontFamily = FontFamily.SansSerif,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 8.dp)
            )
            Text(
                text = receta.otro,
                fontSize = 12.sp,
                fontFamily = FontFamily.SansSerif,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

val recetaInfo = listOf<Receta>(
    Receta("Paracetamol 500 miligramos","1 Tableta. ","Via oral","2 veces al dia por 30 dias", "Tomar dos tabletas solo en caso de mucho dolor"),
    Receta("Paracetamol 500 miligramos","1 Tableta. ","Via oral","2 veces al dia por 30 dias", "Tomar dos tabletas solo en caso de mucho dolor"),
    Receta("Paracetamol 500 miligramos","1 Tableta. ","Via oral","2 veces al dia por 30 dias", "Tomar dos tabletas solo en caso de mucho dolor"),
    Receta("Paracetamol 500 miligramos","1 Tableta. ","Via oral","2 veces al dia por 30 dias", "Tomar dos tabletas solo en caso de mucho dolor"),
    Receta("Paracetamol 500 miligramos","1 Tableta. ","Via oral","2 veces al dia por 30 dias", "Tomar dos tabletas solo en caso de mucho dolor"),
    Receta("Paracetamol 500 miligramos","1 Tableta. ","Via oral","2 veces al dia por 30 dias", "Tomar dos tabletas solo en caso de mucho dolor"),
    Receta("Paracetamol 500 miligramos","1 Tableta. ","Via oral","2 veces al dia por 30 dias", "Tomar dos tabletas solo en caso de mucho dolor"),
    Receta("Paracetamol 500 miligramos","1 Tableta. ","Via oral","2 veces al dia por 30 dias", "Tomar dos tabletas solo en caso de mucho dolor"),
    Receta("Paracetamol 500 miligramos","1 Tableta. ","Via oral","2 veces al dia por 30 dias", "Tomar dos tabletas solo en caso de mucho dolor")
)

data class Receta(
    val medicamento: String,
    val cantidad: String,
    val admin: String,
    val periodo: String,
    val otro: String
)