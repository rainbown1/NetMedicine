package com.rainbown.netmedicine.Dataa

import android.content.Context
import com.rainbown.netmedicine.domain.entity.UserEntity
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.coroutines.suspendCancellableCoroutine
import org.json.JSONObject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class UserRepository(private val context: Context) {

    suspend fun fetchUserByEmail(correo: String): UserEntity? =
        suspendCancellableCoroutine { continuation ->

            val url = "http://192.168.1.11/Api_NetMedicine/GetUsuario.php"
            val queue = Volley.newRequestQueue(context)

            val request = object : StringRequest(
                Method.POST, url,
                { response ->
                    try {
                        val json = JSONObject(response)

                        if (json.getBoolean("success")) {


                            val u = json.getJSONObject("usuario")

                            val user = UserEntity(
                                id = u.getInt("id_usuario"),
                                nombre = u.getString("nombre"),
                                apellido = "",
                                correo = u.getString("correo"),
                                telefono = u.getString("telefono"),
                                contraseña = "",
                                genero = u.optString("genero", ""),
                                peso = u.optString("peso", ""),
                                altura = u.optString("altura", "")
                            )

                            continuation.resume(user)

                        } else {
                            continuation.resume(null)
                        }

                    } catch (e: Exception) {
                        continuation.resumeWithException(e)
                    }
                },
                { error ->
                    continuation.resumeWithException(error)
                }
            ) {
                override fun getParams(): MutableMap<String, String> =
                    hashMapOf("Correo" to correo)
            }

            queue.add(request)
        }
}
