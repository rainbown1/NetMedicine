package com.rainbown.netmedicine

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.rainbown.netmedicine.View.CalendarWithTasks
import com.rainbown.netmedicine.View.Login
import com.rainbown.netmedicine.View.Registro
import com.rainbown.netmedicine.View.Servicios
import com.rainbown.netmedicine.View.home
import com.rainbown.netmedicine.View.inicio
import com.rainbown.netmedicine.navegacion.Nav
import com.rainbown.netmedicine.ui.theme.NetMedicineTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NetMedicineTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                Nav()
                //home(modifier = Modifier.padding(innerPadding))
                    //Login(modifier = Modifier.padding(innerPadding))
                    //Registro(modifier = Modifier.padding(innerPadding))
                    //inicio(modifier = Modifier.padding(innerPadding))

                    //Servicios()

                    //CalendarWithTasks(modifier = Modifier.padding(innerPadding))

                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NetMedicineTheme {
        Greeting("Android")
    }
}