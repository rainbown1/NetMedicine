package com.rainbown.netmedicine.viewmodel

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraPermissionHandler(
    onPermissionGranted: () -> Unit,
    onPermissionDenied: () -> Unit
) {
    val context = LocalContext.current
    val permissionState = rememberPermissionState(android.Manifest.permission.CAMERA)

    LaunchedEffect(permissionState.status) {
        when (permissionState.status) {
            PermissionStatus.Granted -> onPermissionGranted()
            is PermissionStatus.Denied -> {
                if (!(permissionState.status as PermissionStatus.Denied).shouldShowRationale) {
                    onPermissionDenied()
                }
            }
        }
    }

    if (permissionState.status.shouldShowRationale) {
        AlertDialog(
            onDismissRequest = { onPermissionDenied() },
            title = { Text("Permiso de cámara requerido") },
            text = { Text("Esta app necesita acceso a la cámara para tomar fotos de perfil") },
            confirmButton = {
                Button(onClick = { permissionState.launchPermissionRequest() }) {
                    Text("Conceder permiso")
                }
            },
            dismissButton = {
                TextButton(onClick = { onPermissionDenied() }) {
                    Text("Cancelar")
                }
            }
        )
    } else if (permissionState.status is PermissionStatus.Denied) {
        LaunchedEffect(Unit) {
            permissionState.launchPermissionRequest()
        }
    }
}