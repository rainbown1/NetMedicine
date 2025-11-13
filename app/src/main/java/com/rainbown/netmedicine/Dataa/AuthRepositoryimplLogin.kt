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

            val url = "http://192.168.1.3/Api_NetMedicine/login.php"
            val queue = Volley.newRequestQueue(context)

            val request = object : StringRequest(
                Request.Method.POST, url,
                { response ->
                    try {
                        val json = JSONObject(response)

                        if (json.optBoolean("success") && json.has("usuario")) {
                            val usuarioJson = json.getJSONObject("usuario")

                            val user = UserEntity(
                                id = usuarioJson.getInt("idUsuario"),
                                nombre = usuarioJson.getString("Nombre"),
                                apellido = usuarioJson.getString("Apellido"),
                                correo = usuarioJson.getString("Correo"),
                                telefono = usuarioJson.getString("Telefono"),
                                contraseña = usuarioJson.getString("Contraseña")
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
                    hashMapOf(
                        "correo" to correo,
                        "contraseña" to contraseña
                    )
            }

            queue.add(request)
        }

}
