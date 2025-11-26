package com.rainbown.netmedicine.Dataa


import android.content.Context
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.rainbown.netmedicine.Domainn.repository.RegistreRepository
import com.rainbown.netmedicine.domain.entity.UserEntity


import kotlinx.coroutines.suspendCancellableCoroutine
import org.json.JSONObject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class AuthRepositoryimplRegistree(private val context: Context) : RegistreRepository {

    override suspend fun registre(
        nombre: String,
        apellido: String,
        correo: String,
        telefono: String,
        contraseña: String,
        genero: String,
        peso: String,
        altura: String
    ): UserEntity? = suspendCancellableCoroutine { continuation ->

        val url = "http://192.168.1.5/Api_NetMedicine/registro.php"
        val queue = Volley.newRequestQueue(context)

        val stringRequest = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener<String> { response ->
                try {
                    val jsonResponse = JSONObject(response)
                    val success = jsonResponse.optBoolean("success", false)

                    if (success) {
                        val id = jsonResponse.optInt("id", 0)

                        val user = UserEntity(
                            id = id,
                            nombre = nombre,
                            apellido = apellido,
                            correo = correo,
                            telefono = telefono,
                            contraseña = contraseña,
                            genero = genero,
                            peso = peso,
                            altura = altura
                        )

                        Toast.makeText(context, "Usuario registrado correctamente", Toast.LENGTH_LONG).show()
                        continuation.resume(user)
                    } else {
                        Toast.makeText(context, "Error al registrar usuario", Toast.LENGTH_LONG).show()
                        continuation.resume(null)
                    }

                } catch (e: Exception) {
                    continuation.resumeWithException(e)
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(context, "Error: ${error.message}", Toast.LENGTH_LONG).show()
                continuation.resumeWithException(error)
            }
        ) {
            override fun getParams(): MutableMap<String, String> {
                return hashMapOf(
                    "nombre" to nombre,
                    "apellido" to apellido,
                    "telefono" to telefono,
                    "correo" to correo,
                    "contraseña" to contraseña,
                    "genero" to genero,
                    "peso" to peso,
                    "altura" to altura
                )
            }
        }

        queue.add(stringRequest)
    }
}
