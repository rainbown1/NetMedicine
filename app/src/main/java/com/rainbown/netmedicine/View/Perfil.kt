package com.rainbown.netmedicine.View

import android.content.Context
import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Scale
import androidx.compose.material.icons.filled.Height
import androidx.compose.material.icons.filled.Male
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.rainbown.netmedicine.Dataa.UserRepository
import com.rainbown.netmedicine.View.Components.MyNavigationBar
import com.rainbown.netmedicine.domain.entity.UserEntity
import com.rainbown.netmedicine.navegacion.ScreenNav
import com.rainbown.netmedicine.ui.theme.onPrimaryContainerLight
import com.rainbown.netmedicine.viewmodel.PerfilViewModel
import com.rainbown.netmedicine.viewmodel.PerfilViewModelFactory
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
        Perfil(modifier = Modifier.padding(innerPadding), navController)
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun Perfil(
    modifier: Modifier,
    navController: NavController
) {

    val context = LocalContext.current

    val userRepository = remember {
        UserRepository(
            context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        )
    }

    val viewModel: PerfilViewModel = viewModel(
        factory = PerfilViewModelFactory(userRepository)
    )

    val savedStateHandle = navController.currentBackStackEntry?.savedStateHandle
    val fotoPerfilPath = savedStateHandle?.get<String>("FOTO_PERFIL")
    val userData by viewModel.userData.observeAsState()
    val fotoPerfil by viewModel.fotoPerfil.observeAsState()

    val currentUserData = userData ?: UserEntity(
        nombre = "Milca Celeste",
        apellido = "Nava De Dios",
        correo = "2124100006@soy.utj.edu.mx",
        telefono = "3321708076",
        genero = "Mujer",
        peso = "45 kg",
        altura = "1.73"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        // Header
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
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape)
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
                    text = "${currentUserData.nombre} ${currentUserData.apellido}",
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif,
                    color = Color.White
                )

                Text(
                    text = "${currentUserData.correo}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.8f)
                )

                Spacer(modifier = Modifier.height(24.dp))
            }
        }

        // Información del usuario
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Tarjeta de información personal
            InfoCard(
                title = "Información Personal",
                items = listOf(
                    InfoItem(Icons.Filled.Male, "Género", "${currentUserData.genero}"),
                    InfoItem(Icons.Filled.Scale, "Peso", "${currentUserData.peso}"),
                    InfoItem(Icons.Filled.Height, "Altura", "${currentUserData.altura}")
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Tarjeta de contacto
            InfoCard(
                title = "Contacto",
                items = listOf(
                    InfoItem(Icons.Filled.Phone, "Teléfono", "${currentUserData.telefono}"),
                    InfoItem(Icons.Filled.Email, "Email", "${currentUserData.correo}")
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Botón de editar
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
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = "Editar",
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Editar Perfil",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedButton(
                onClick = { },
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
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            items.forEach { item ->
                InfoRow(icon = item.icon, label = item.label, value = item.value)
                if (item != items.last()) {
                    Spacer(modifier = Modifier.height(12.dp))
                }
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
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}



data class InfoItem(
    val icon: ImageVector,
    val label: String,
    val value: String
)