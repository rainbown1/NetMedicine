package com.rainbown.netmedicine.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.rainbown.netmedicine.View.pantallaregistro


class RegistroVm: ViewModel() {
    val nombre = MutableLiveData<String>()
    val lastName = MutableLiveData<String>()
    val tel = MutableLiveData<String>()
    val email = MutableLiveData<String>()

    val password = MutableLiveData<String>()

    fun onregistroChanged(nombreValue:String,lastnameValue:String,telValue:String,emailValue: String, passwordValue: String) {
        nombre.value = nombreValue
        lastName.value = lastnameValue
        tel.value = telValue
        email.value = emailValue
        password.value = passwordValue

    }
    fun onregistroSelected(context: Context) {
        val url="http://172.16.100.186/Api_NetMedicine/registro.php"
        val queue = Volley.newRequestQueue(context)
        var resultadoPost= object: StringRequest(
            Request.Method.POST,url,
            Response.Listener<String>{ response ->
                Toast.makeText(context, "Usuario Insertado", Toast.LENGTH_LONG).show()


            }
        , Response.ErrorListener{
            error->
                Toast.makeText(context, "Error $error", Toast.LENGTH_LONG).show()

            }
            ){
            override fun getParams(): MutableMap<String, String> {
               val parametros= HashMap<String, String>()
                parametros["nombre"] = nombre.value ?: ""
                parametros["apellido"] = lastName.value ?: ""
                parametros["telefono"] = tel.value ?: ""
                parametros["email"] = email.value ?: ""
                parametros["password"] = password.value ?: ""
                return parametros
            }
        }
        queue.add(resultadoPost)
    }
}