package com.rainbown.netmedicine.View.Componentes

import android.Manifest
import android.content.Context
import android.os.Environment
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import java.io.File
import java.util.concurrent.Executor

@Composable
fun pantallacamara(navController: NavController){
    Scaffold(modifier = Modifier.fillMaxSize()) {innerPadding ->
        Camara(navController = navController, modifier = Modifier.padding(innerPadding))
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun Camara(navController: NavController, modifier: Modifier){
    var context = LocalContext.current
    val cameraController = remember {
        LifecycleCameraController(context)
    }
    val lifecycle = LocalLifecycleOwner.current
    cameraController.cameraSelector =
        CameraSelector.DEFAULT_FRONT_CAMERA

    val permissionState = rememberPermissionState(permission = Manifest.permission.CAMERA)
    LaunchedEffect(Unit) {
        permissionState.launchPermissionRequest()
    }
    ConstraintLayout {
        val boton = createRefs()
        Scaffold (modifier = Modifier.fillMaxSize(), floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    takePicture(
                        cameraController = cameraController,
                        executor = ContextCompat.getMainExecutor(context),
                        context = context,
                        navController = navController
                    )
                },
            ) {
                Text("Camara!")
            }
        }) {
            if (permissionState.status.isGranted) {
                CameraComposable(modifier = Modifier.padding(it),cameraController = cameraController, lifecycle = lifecycle)
            } else {
                Text("Permiso Denegado", modifier = Modifier.padding(it))
            }
        }
    }
}

@Composable
fun CameraComposable(
    modifier: Modifier = Modifier,
    cameraController: LifecycleCameraController,
    lifecycle: LifecycleOwner
){


    cameraController.bindToLifecycle(lifecycle)
    ConstraintLayout {
        val (previewRef, buttonRef) = createRefs()

        AndroidView(
            modifier = Modifier
                .constrainAs(previewRef) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxSize(),
            factory = { context ->
            val previewView = PreviewView(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            }
            previewView.controller = cameraController

            previewView
        })
    }
}

private fun takePicture(
    cameraController: LifecycleCameraController,
    executor: Executor,
    context: Context,
    navController: NavController
) {

    // Carpeta pública de fotos
    val directory = File(
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
        "NetMedicine"
    )

    // Crear carpeta si no existe
    if (!directory.exists()) {
        directory.mkdirs()
    }

    // Archivo final donde se guardará la foto
    val file = File(directory, "foto_perfil.jpg")

    val outputFileOptions = ImageCapture.OutputFileOptions.Builder(file).build()

    cameraController.takePicture(
        outputFileOptions,
        executor,
        object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                navController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.set("FOTO_PERFIL", file.absolutePath)

                navController.popBackStack()
            }

            override fun onError(exception: ImageCaptureException) {
                println("Error al capturar imagen: ${exception.message}")
            }
        }
    )
}

