package com.rainbown.netmedicine.View

import PerfilViewModel
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.rainbown.netmedicine.Dataa.UserRepository
import com.rainbown.netmedicine.View.Components.MyNavigationBar
import com.rainbown.netmedicine.domain.entity.UserEntity
import com.rainbown.netmedicine.navegacion.ScreenNav
import com.rainbown.netmedicine.ui.theme.onPrimaryContainerLight
import com.rainbown.netmedicine.viewmodel.PerfilViewModelFactory
import kotlinx.coroutines.launch
import java.io.File

@Composable
fun pantallaperfil(navController: NavController) {
    Scaffold(
        modifier = Modifier,
        bottomBar = {
            Box(modifier = Modifier.padding(8.dp)) {
                MyNavigationBar(navController)
            }
        }
    ) { innerPadding ->
        Perfil(
            modifier = Modifier.padding(innerPadding),
            navController = navController
        )
    }
}

@Composable
fun Perfil(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val context = LocalContext.current

    val viewModel: PerfilViewModel = viewModel(
        factory = PerfilViewModelFactory(UserRepository(context))
    )

    val userData by viewModel.userData.observeAsState()
    val fotoPerfil by viewModel.fotoPerfil.observeAsState()
    val savedStateHandle = navController.currentBackStackEntry?.savedStateHandle
    val fotoPerfilPath = savedStateHandle?.get<String>("FOTO_PERFIL")



    LaunchedEffect(Unit) {
        val sharedPref = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val correoUsuario = sharedPref.getString("correo", null)

        if (correoUsuario != null) {
            println("Cargando perfil para $correoUsuario")
            viewModel.loadUserByEmail(correoUsuario)
        } else {
            println("No se encontró correo guardado")
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {


        if (userData == null) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
            return
        }

        val currentUser = userData!!


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(onPrimaryContainerLight)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                // Foto de perfil
                IconButton(
                    onClick = {
                        navController.navigate(route = ScreenNav.pantallacamara.route)
                    },
                    modifier = Modifier
                        .size(120.dp)
                        .shadow(8.dp, CircleShape)
                        .border(4.dp, Color.White, CircleShape)
                        .clip(CircleShape)
                ) {
                    val currentFoto = fotoPerfil ?: fotoPerfilPath
                    if (currentFoto != null) {
                        Image(
                            painter = rememberAsyncImagePainter(File(currentFoto)),
                            contentDescription = "Foto de perfil",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(MaterialTheme.colorScheme.surfaceVariant),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Person,
                                contentDescription = "Usuario",
                                modifier = Modifier.size(60.dp),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "${currentUser.nombre} ${currentUser.apellido}",
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif,
                    color = Color.White
                )

                Text(
                    text = currentUser.correo,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.8f)
                )

                Spacer(modifier = Modifier.height(24.dp))
            }
        }

        // INFORMACIÓN PERSONAL
        Column(modifier = Modifier.padding(16.dp)) {

            InfoCard(
                title = "Información Personal",
                items = listOf(
                    InfoItem(Icons.Filled.Male, "Género", currentUser.genero),
                    InfoItem(Icons.Filled.Scale, "Peso", currentUser.peso),
                    InfoItem(Icons.Filled.Height, "Altura", currentUser.altura)
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            InfoCard(
                title = "Contacto",
                items = listOf(
                    InfoItem(Icons.Filled.Phone, "Teléfono", currentUser.telefono),
                    InfoItem(Icons.Filled.Email, "Email", currentUser.correo)
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { navController.navigate(ScreenNav.pantallaeditarperfil.route) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = onPrimaryContainerLight,
                    contentColor = Color.White
                )
            ) {
                Icon(Icons.Filled.Edit, contentDescription = "Editar", modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Editar Perfil",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedButton(
                onClick = {},
                modifier = Modifier.height(50.dp)
            ) {
                Text("Fin de la pagina")
            }
        }
    }
}

@Composable
fun InfoCard(title: String, items: List<InfoItem>) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            items.forEach { item ->
                InfoRow(item.icon, item.label, item.value)
                if (item != items.last()) Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
fun InfoRow(icon: ImageVector, label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(Modifier.weight(1f)) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

data class InfoItem(
    val icon: ImageVector,
    val label: String,
    val value: String
)
