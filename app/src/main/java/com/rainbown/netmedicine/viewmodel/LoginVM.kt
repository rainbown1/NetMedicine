package com.rainbown.netmedicine.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

data class Usuario(
    val id: Int,
    val nombre: String,
    val apellido: String,
    val correo: String,
    val telefono: String,
    val contraseña: String = ""
)



class LoginVM: ViewModel(){
val email = MutableLiveData<String>()
    val usr = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val usuarioLiveData = MutableLiveData<Usuario?>()
    val errorLiveData = MutableLiveData<String?>()
    fun onLoginChanged(emailValue: String, usrValue:String, passwordValue: String) {
        email.value = emailValue
        usr.value = usrValue
        password.value = passwordValue

    }
        fun onLoginSelected(context: Context) {
            val url = "http://192.168.137.1/Api_NetMedicine/login.php"
            val queue = Volley.newRequestQueue(context)

            val request = object : StringRequest(
                Request.Method.POST, url,
                { response ->
                    try {
                        val json = JSONObject(response)
                        if (json.has("error")) {
                            errorLiveData.postValue(json.getString("error"))
                        } else {
                            val usuario = Usuario(
                                id = json.getInt("idUsuario"),
                                nombre = json.getString("Nombre"),
                                apellido = json.getString("Apellido"),
                                correo = json.getString("Correo"),
                                telefono = json.getString("Telefono"),
                                contraseña = "" // no viene, lo dejamos vacío
                            )
                            usuarioLiveData.postValue(usuario)



                            usuarioLiveData.postValue(usuario)
                        }
                    } catch (e: Exception) {
                        errorLiveData.postValue("Error al procesar respuesta: ${e.message}")
                    }
                },
                { error ->
                    errorLiveData.postValue("Error en la conexión: ${error.message}")
                }
            ) {
                override fun getParams(): MutableMap<String, String> {
                    val params = HashMap<String, String>()
                    params["correo"] = email.value ?: ""
                    params["contraseña"] = password.value ?: ""
                    return params
                }
            }

            queue.add(request)
        }
        }


