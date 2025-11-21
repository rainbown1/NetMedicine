package com.rainbown.netmedicine.Dataa

import RecetRepository
import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.rainbown.netmedicine.Domainn.entity.RecetEntity
import com.rainbown.netmedicine.domain.entity.UserEntity
import com.rainbown.netmedicine.domain.repository.AuthRepository
import kotlinx.coroutines.suspendCancellableCoroutine
import org.json.JSONObject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class ReepositoryimplRecet(private val context: Context) : RecetRepository {

    override suspend fun obtenerRecetas(correo: String): List<RecetEntity> =
        suspendCancellableCoroutine { continuation ->

            val url = "http://192.168.1.5/Api_NetMedicine/Receta.php"
            val queue = Volley.newRequestQueue(context)

            val request = object : StringRequest(
                Method.POST, url,
                StringRequest@{ response ->
                    try {
                        println("RESPUESTA RECETAS ===> $response")

                        val json = JSONObject(response)

                        if (!json.optBoolean("success")) {
                            continuation.resume(emptyList())
                            return@StringRequest
                        }

                        val listaJson = json.getJSONArray("recetas")
                        val recetas = mutableListOf<RecetEntity>()

                        for (i in 0 until listaJson.length()) {
                            val obj = listaJson.getJSONObject(i)

                            recetas.add(
                                RecetEntity(
                                    idReceta = obj.getInt("idReceta"),
                                    correo = obj.getString("correo"),
                                    medicamento = obj.getString("medicamento"),
                                    cantidad = obj.getString("cantidad"),
                                    admin = obj.getString("admi"),
                                    periodo = obj.getString("periodo")
                                )
                            )
                        }

                        continuation.resume(recetas)

                    } catch (e: Exception) {
                        continuation.resumeWithException(e)
                    }
                },
                { error ->
                    continuation.resumeWithException(error)
                }
            ) {
                override fun getParams(): MutableMap<String, String> {
                    val params = HashMap<String, String>()
                    params["correo"] = correo
                    println("Enviando correo al servidor: $correo")
                    return params
                }
            }

            queue.add(request)
        }
}
