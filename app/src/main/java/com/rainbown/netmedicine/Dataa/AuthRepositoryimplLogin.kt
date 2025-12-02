package com.rainbown.netmedicine.data.repository

import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.rainbown.netmedicine.domain.entity.UserEntity
import com.rainbown.netmedicine.domain.repository.AuthRepository
import kotlinx.coroutines.suspendCancellableCoroutine
import org.json.JSONObject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class AuthRepositoryImpl(private val context: Context) : AuthRepository {

    override suspend fun login(correo: String, contraseña: String): UserEntity? =
        suspendCancellableCoroutine { continuation ->

            val url = "http://192.168.1.11/Api_NetMedicine/login.php"
            val queue = Volley.newRequestQueue(context)

            val request = object : StringRequest(
                Request.Method.POST, url,
                StringRequest@{ response ->
                    try {
                        val json = JSONObject(response)

                        val success = json.optBoolean("success")
                        val message = json.optString("message")


                        if (!success) {
                            continuation.resumeWithException(Exception(message))
                            return@StringRequest
                        }

                        val usuarioJson = json.getJSONObject("usuario")

                        val user = UserEntity(
                            id = usuarioJson.getInt("id_usuario"),
                            nombre = usuarioJson.getString("nombre"),
                            apellido = "",
                            correo = usuarioJson.getString("correo"),
                            telefono = usuarioJson.getString("telefono"),
                            contraseña = usuarioJson.getString("password"),
                            genero = "",
                            peso = "",
                            altura = ""
                        )

                        continuation.resume(user)

                    } catch (e: Exception) {
                        continuation.resumeWithException(e)
                    }
                },
                { error ->
                    continuation.resumeWithException(Exception("Error de conexión al servidor"))
                }
            ) {
                override fun getParams(): MutableMap<String, String> =
                    hashMapOf(
                        "correo" to correo,
                        "contraseña" to contraseña
                    )
            }

            queue.add(request)
        }
}
