package com.rainbown.netmedicine.View

import android.Manifest
import android.content.Context
import android.view.ViewGroup
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.rainbown.netmedicine.R
import com.rainbown.netmedicine.View.Components.MyNavigationBar
import com.rainbown.netmedicine.View.Components.barra
import com.rainbown.netmedicine.navegacion.ScreenNav
import com.rainbown.netmedicine.ui.theme.onPrimaryContainerLight
import org.jetbrains.annotations.Async
import java.io.File
import java.util.concurrent.Executor

@Composable
fun pantallaperfil(navController: NavController){
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        PerfilScreen(navController, modifier = Modifier.padding(innerPadding))
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PerfilScreen(navController: NavController, modifier: Modifier){

    ConstraintLayout (modifier = Modifier.fillMaxSize() ){

        val (barra,img,content,menu) = createRefs()
        val radioOptions = listOf("Hombre", "Mujer", "Otro")
        val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }

        Box(
            modifier = Modifier.constrainAs(barra){
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                end.linkTo(parent.end)
            }
        ){
            barra("Perfil")
        }

        Box(modifier = Modifier.constrainAs(menu){
            start.linkTo(parent.start)
            bottom.linkTo(parent.bottom)
            end.linkTo(parent.end)
        }){
            MyNavigationBar(navController)
        }

        Box(modifier = Modifier.fillMaxHeight().constrainAs(content){
            top.linkTo(barra.bottom, margin = 20.dp)
            start.linkTo(parent.start)
        }){
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {

                Row {
                    IconButton(
                        onClick = {
                            navController.navigate(route = ScreenNav.pantallacamara.route)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Person,
                            contentDescription = "Usuario"
                        )
                    }
                    Spacer(modifier = Modifier.size(16.dp))
                    Column {
                        Text("Nombre del usuario")
                        Text("Correo del usuario")
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))

                //Seleccion de genero
                Text("Genero")
                Row (modifier = Modifier.selectableGroup()
                    .padding(15.dp)){
                    radioOptions.forEach { text ->
                        Row(
                            Modifier
                                .height(45.dp)
                                .selectable(
                                    selected = (text == selectedOption),
                                    onClick = { onOptionSelected(text) },
                                    role = Role.RadioButton
                                )
                                .padding(horizontal = 5.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = (text == selectedOption),
                                onClick = null // null recommended for accessibility with screen readers
                            )
                            Text(
                                text = text,
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(start = 16.dp),
                                fontFamily = FontFamily.Serif,
                                fontSize = 12.sp
                            )
                        }
                    }
                }

                datosList.forEach {text ->
                    Text(text.titulo)
                    OutlinedTextField(
                        state = rememberTextFieldState(),
                        label =  { Text(
                            text.label1,
                            fontFamily = FontFamily.Serif,
                            fontSize = 12.sp,
                            color = Color.Black)}
                    )
                    OutlinedTextField(
                        state = rememberTextFieldState(),
                        label =  { Text(
                            text.label2,
                            fontFamily = FontFamily.Serif,
                            fontSize = 12.sp,
                            color = Color.Black)}
                    )
                }

               Row (modifier = Modifier.padding(top = 10.dp)) {
                   Button(
                       onClick = { },
                       colors = ButtonDefaults.buttonColors(
                           containerColor = onPrimaryContainerLight,
                           contentColor = Color.White
                       )
                   ) {
                       Text("Guardar")
                   }
                   Spacer(modifier = Modifier.width(15.dp))
                   OutlinedButton(
                       onClick = { }
                   ) {
                       Text("Cerrar sesión")
                   }
               }

                Spacer(modifier = Modifier.height(150.dp))
                //Este boton no hace nada, solo esta para que funcione el scroll
                Button(
                    onClick = { },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = onPrimaryContainerLight,
                        contentColor = Color.White
                    )
                ) {
                    Text("Guardar")
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


val datosList = listOf<Datos>(
    Datos("Nombre", "Nombre", "Apellidos"),
    Datos("Contacto","Numero de telefono", "Correo electronico"),
    Datos("Datos personales","Peso", "Altura"),

)
data class Datos(
    val titulo: String,
    val label1: String,
    val label2: String
)


